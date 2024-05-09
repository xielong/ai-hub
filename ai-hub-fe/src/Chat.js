import React, {useEffect, useRef, useState} from 'react';
import Button from '@mui/material/Button';
import TextField from '@mui/material/TextField';

import './Chat.css';
import Sidebar from "./Sidebar";
import ReactMarkdown from "react-markdown";

function Chat() {
    const [input, setInput] = useState('');
    const [selectedModels, setSelectedModels] = useState({
        'OpenAI/gpt-3.5-turbo-0125': true,
        'Baidu/ERNIE-Bot-turbo': true,
        'Ali/qwen-turbo': true,
        'Tencent/ChatStd': true,
        'Baichuan/Baichuan2-Turbo': true,
        'Zhipu/chatGLM_turbo': true,
        'Minimax/abab5.5-chat': true,
        'Xunfei/Spark3.1': true,
        'Xunfei/Spark3.5': true,
        'Ali/qwen-plus': true,
        'Moonshot/moonshot-v1-8k': true,
        'ByteDance/Skylark-chat': true,
        'Lingyi/yi-34b-chat-0205': true,
        'Lingyi/yi-34b-chat-200k': true,
        'Lingyi/yi-vl-plus': true,
        'Deepseek/deepseek-chat': true,

        'Baidu/ERNIE-Bot-4': false,
        'Zhipu/glm-4': false,
        'Tencent/ChatPro': false,
        'Minimax/abab6-chat': false,
        'OpenAI/gpt-4-0125-preview': false,

    });

    const colorClasses = ['message-blue', 'message-green', 'message-coral', 'message-yellow'];

    const [modelColors, setModelColors] = useState({});

    useEffect(() => {
        const initialModelColors = {};
        let colorIndex = 0;
        Object.keys(selectedModels).forEach(model => {
            initialModelColors[model] = colorClasses[colorIndex % colorClasses.length];
            colorIndex++;
        });
        setModelColors(initialModelColors);
    }, [selectedModels]);

    const [messages, setMessages] = useState([]);

    const messagesEndRef = useRef(null);

    const scrollToBottom = () => {
        messagesEndRef.current?.scrollIntoView({behavior: "smooth"});
    };

    useEffect(() => {
        scrollToBottom();
    }, [messages]);

    const handleInputChange = (e) => {
        setInput(e.target.value);
        e.target.style.height = 'auto';
        e.target.style.height = `${e.target.scrollHeight}px`;
    };

    const handleModelChange = (model, isChecked) => {
        console.log(`Model: ${model}, isChecked: ${isChecked}`);
        setSelectedModels(prevModels => ({
            ...prevModels,
            [model]: isChecked
        }));
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        sendMessage();

    };

    const sendMessage = async () => {
        if (!input.trim()) return;

        const newMessages = [...messages, {text: input, sender: 'USER'}];
        setMessages(newMessages);

        setInput('');

        const selectedModelEntries = Object.entries(selectedModels);
        const selectedModelNames = selectedModelEntries
            .filter(([, isChecked]) => isChecked)
            .map(([modelName]) => modelName);

        if (selectedModelNames.length === 0) {
            console.log("No models selected");
            return;
        }

        selectedModelNames.forEach(modelName => {
            const [provider, model] = modelName.split('/');
            const url = `/api/v1/models/${provider}/${model}:chat`;
            fetch(url, {
                method: 'POST',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify({input})
            })
                .then(response => {
                    if (!response.ok) {
                        throw new Error(`HTTP error! Status: ${response.status}`);
                    }
                    return response.json();
                })
                .then(data => {
                    setMessages(messages => [...messages, {text: data.output, sender: modelName}]);
                })
                .catch(error => {
                    console.error('Error sending message to model:', modelName, error);
                });
        });
    };


    return (
        <div className="chat-container">
            <Sidebar selectedModels={selectedModels} handleModelChange={handleModelChange}/>
            <div className="main-content">
                <div className="message-area">
                    {messages.map((msg, index) => {
                        const messageClass = `ai-message ${modelColors[msg.sender] || ''}`;

                        return (
                            <p key={index} className={msg.sender === 'USER' ? 'user-message' : messageClass}>
                                <strong>{msg.sender}</strong> <br/>
                                <ReactMarkdown>{msg.text}</ReactMarkdown>
                            </p>
                        );
                    })}
                    <div ref={messagesEndRef}/>
                </div>

                <form onSubmit={handleSubmit}>
                    <div className="input-area">
                        <TextField
                            className={"input-textarea"}
                            value={input}
                            multiline
                            onChange={handleInputChange}
                            onKeyPress={e => e.key === 'Enter' && !e.shiftKey && handleSubmit(e)}
                            maxRows={5}
                        />
                        <Button variant="contained" type="submit" style={{height: '50px'}}>Send</Button>
                    </div>
                </form>


            </div>
        </div>
    );
}

export default Chat;
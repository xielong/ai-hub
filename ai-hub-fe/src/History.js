import {Accordion, AccordionDetails, AccordionSummary, List, Typography} from "@mui/material";
import {useEffect, useState} from "react";

import './History.css';
import Sidebar from "./Sidebar";

function History() {
    const [questions, setQuestions] = useState([]);
    const [answers, setAnswers] = useState([]);

    useEffect(() => {
        fetch('/api/v1/history/questions')
            .then(response => response.json())
            .then(data => {
                setQuestions(data);
            })
            .catch(error => {
                console.error('Error fetching data: ', error);
            });
    }, []); // 空数组确保仅在组件挂载时执行

    const fetchAnswers = (questionHash) => {
        if (!answers[questionHash]) {
            fetch(`/api/v1/history/questions/${questionHash}/answers`)
                .then(response => response.json())
                .then(data => {
                    setAnswers(prevAnswers => ({...prevAnswers, [questionHash]: data}));
                })
                .catch(error => {
                    console.error('Error fetching answers: ', error);
                });
        }
    };

    const selectedModels = {/* ... */};
    const handleModelChange = {/* ... */};

    return (
        <div className="main-container">
            <Sidebar selectedModels={selectedModels} handleModelChange={handleModelChange}/>
            <List style={{width: '80%', marginLeft: '20%'}}>
                {questions.map((item, index) => (
                    <Accordion key={index} onChange={() => fetchAnswers(item.hash)}>
                        <AccordionSummary>
                            <Typography style={{fontWeight: 'bold'}}>Prompt: {item.question}</Typography>
                        </AccordionSummary>
                        <AccordionDetails>
                            <Typography>
                                {answers[item.hash] ? answers[item.hash].map((answer, idx) => (
                                    <p key={idx}>{answer.providerName}: {answer.answer}</p>
                                )) : 'Loading...'}
                            </Typography>
                        </AccordionDetails>
                    </Accordion>
                ))}
            </List>
        </div>
    );

}

export default History;
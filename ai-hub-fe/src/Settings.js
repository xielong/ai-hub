import React, {useState} from 'react';
import Box from '@mui/material/Box';
import TextField from '@mui/material/TextField';
import './Settings.css';
import Button from "@mui/material/Button";
import {Grid} from "@mui/material";
import Sidebar from "./Sidebar";


function Settings() {

    const models = [
        {provider: 'OpenAI', credentials: ['token']},
        {provider: 'Baichuan', credentials: ['token']},
        {provider: 'Zhipu', credentials: ['apiSecretKey']},
        {provider: 'Ali', credentials: ['apiKey']},
        {provider: 'Baidu', credentials: ['apiKey', 'secretKey']},
        {provider: 'Minimax', credentials: ['groupId', 'apiKey']},
        {provider: 'Tencent', credentials: ['appId', 'secretId', 'secretKey']},
    ];

    const selectedModels = {/* ... */};
    const handleModelChange = {/* ... */};

    const [credentials, setCredentials] = useState({});

    const handleCredentialChange = (modelName, credential, value) => {
        setCredentials({
            ...credentials,
            [modelName]: {
                ...credentials[modelName],
                [credential]: value
            }
        });
    };

    const handleSubmit = (provider, e) => {
        e.preventDefault();

        const requestBody = Object.entries(credentials).map(([model, creds]) => {
            return Object.entries(creds).map(([key, value]) => {
                return {key, value};
            });
        }).flat();

        fetch(`/api/v1/credentials/${provider}`, {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(requestBody)
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error(`HTTP error! Status: ${response.status}`);
                }
                return response.json();
            })
            .then(data => {
                console.log('Credentials saved successfully', data);
            })
            .catch(error => {
                console.error('Error updating credentials:', error);
            });
    };

    return (
        <div className="settings-container">
            <Sidebar selectedModels={selectedModels} handleModelChange={handleModelChange}/>
            <Box
                component="form"
                sx={{
                    marginTop: '40px',
                    marginLeft: '40px',
                    '& .MuiGrid-item': {
                        marginBottom: '16px', // 设置每个 Grid 项目的底部边距
                    }
                }}
                noValidate
                autoComplete="off"
            >
                {models.map((model, index) => (
                    <Grid container key={index} alignItems="center" spacing={6}>
                        {/* 模型名称 */}
                        <Grid item xs={12} sm={2}>
                            <h2 style={{margin: 0, paddingLeft: '16px'}}>{model.provider}</h2>
                        </Grid>

                        {/* 凭证输入框 */}
                        {model.credentials.map((cred, credIndex) => (
                            <Grid item xs={12}
                                  sm={model.credentials.length === 1 ? 8 : model.credentials.length === 2 ? 4 : 2.7}
                                  key={credIndex}>
                                <TextField
                                    label={cred}
                                    type="password"
                                    size="small"
                                    fullWidth
                                    value={credentials[model.provider]?.[cred] || ''}
                                    onChange={(e) => handleCredentialChange(model.provider, cred, e.target.value)}
                                />
                            </Grid>
                        ))}

                        {/* 提交按钮 */}
                        <Grid item xs={12} sm={1}>
                            <Button variant="contained" onClick={(e) => handleSubmit(model.provider, e)}>Submit</Button>
                        </Grid>
                    </Grid>
                ))}
            </Box>
        </div>
    );

}

export default Settings;
import React, {useState} from 'react';
import Box from '@mui/material/Box';
import TextField from '@mui/material/TextField';
import './Settings.css';
import Button from "@mui/material/Button";
import {Grid, Snackbar} from "@mui/material";
import Sidebar from "./Sidebar";
import MuiAlert from '@mui/material/Alert';

function Settings() {

    const models = [
        {provider: 'OpenAI', credentials: ['token']},
        {provider: 'Baichuan', credentials: ['token']},
        {provider: 'Zhipu', credentials: ['apiSecretKey']},
        {provider: 'Ali', credentials: ['apiKey']},
        {provider: 'Baidu', credentials: ['apiKey', 'secretKey']},
        {provider: 'Minimax', credentials: ['groupId', 'apiKey']},
        {provider: 'Tencent', credentials: ['appId', 'secretId', 'secretKey']},
        {provider: 'Xunfei', credentials: ['appId', 'apiKey', 'apiSecret']},
        {provider: 'Moonshot', credentials: ['apiKey']},
        {provider: 'ByteDance', credentials: ['accessKey', 'secretKey']},
        {provider: 'Lingyi', credentials: ['apiKey']},
    ];

    const selectedModels = {/* ... */};
    const handleModelChange = {/* ... */};

    const [credentials, setCredentials] = useState({});

    const [open, setOpen] = useState(false);

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

        const modelCredentials = credentials[provider] || {};

        const requiredCredentials = models.find(model => model.provider === provider)?.credentials || [];
        const allCredentialsFilled = requiredCredentials.every(cred =>
            modelCredentials[cred] != null && modelCredentials[cred].trim() !== ''
        );

        if (!allCredentialsFilled) {
            console.info('Please fill in all the credentials for', provider);
            return;
        }

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
                setOpen(true);
            })
            .catch(error => {
                console.error('Error updating credentials:', error);
            });
    };

    const handleClose = (event, reason) => {
        if (reason === 'clickaway') {
            return;
        }
        setOpen(false);
    };

    return (
        <div>
            <div className="main-container">
                <Sidebar selectedModels={selectedModels} handleModelChange={handleModelChange}/>
                <Box
                    component="form"
                    sx={{
                        marginTop: '40px',
                        marginLeft: '20%',
                        '& .MuiGrid-item': {
                            marginBottom: '16px',
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

                            <Grid item xs={12} sm={1}>
                                <Button variant="contained"
                                        onClick={(e) => handleSubmit(model.provider, e)}>Submit</Button>
                            </Grid>
                        </Grid>
                    ))}
                </Box>
            </div>

            <Snackbar
                open={open}
                autoHideDuration={2000}
                onClose={handleClose}
                anchorOrigin={{vertical: 'top', horizontal: 'center'}}
            >
                <MuiAlert onClose={handleClose} severity="success" elevation={6} variant="filled">
                    Credentials saved successfully!
                </MuiAlert>
            </Snackbar>
        </div>
    );

}

export default Settings;
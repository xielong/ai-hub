import React from 'react';
import Box from '@mui/material/Box';
import TextField from '@mui/material/TextField';
import './Settings.css';
import Button from "@mui/material/Button";
import {Grid} from "@mui/material";


function Settings() {

    const models = [
        {name: 'OpenAI', credentials: ['token']},
        {name: 'Baichuan', credentials: ['token']},
        {name: 'Zhipu', credentials: ['apiSecretKey']},
        {name: 'Baidu', credentials: ['apiKey', 'secretKey']},
        {name: 'Ali', credentials: ['apiKey']},
        {name: 'MiniMax', credentials: ['apiKey']},
        {name: 'Xunfei', credentials: ['apiKey', 'apiSecret']},
        {name: 'Tencent', credentials: ['appId', 'secretId', 'secretKey']},
        // 更多模型...
    ];

    return (
        <Box
            component="form"
            sx={{
                '& .MuiTextField-root': {m: 1, width: '25ch'},
            }}
            noValidate
            autoComplete="off"
        >
            {models.map((model, index) => (
                <Grid container key={index} alignItems="center" spacing={2}>
                    <Grid item xs={1}>
                        <h2 style={{margin: 0, paddingLeft: '16px'}}>{model.name}</h2>
                    </Grid>
                    {model.credentials.map((cred, credIndex) => (
                        <Grid item key={credIndex}>
                            <TextField
                                label={cred}
                                type="password"
                                autoComplete="current-password"
                                size="small"
                            />
                        </Grid>
                    ))}
                    <Grid item>
                        <Button variant="contained">Submit</Button>
                    </Grid>
                </Grid>
            ))}
        </Box>
    );

}

export default Settings;
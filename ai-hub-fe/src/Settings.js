import React from 'react';
import Box from '@mui/material/Box';
import TextField from '@mui/material/TextField';
import './Settings.css';
import Button from "@mui/material/Button";
import {Grid} from "@mui/material";
import Sidebar from "./Sidebar";


function Settings() {

    const models = [
        {name: 'OpenAI', credentials: ['token']},
        {name: 'Baichuan', credentials: ['token']},
        {name: 'Zhipu', credentials: ['apiSecretKey']},
        {name: 'Ali', credentials: ['apiKey']},
    ];

    const selectedModels = {/* ... */};
    const handleModelChange = {/* ... */};

    return (
        <div className="settings-container">
            <Sidebar selectedModels={selectedModels} handleModelChange={handleModelChange}/>
            <Box
                component="form"
                sx={{
                    '& .MuiTextField-root': {m: 1, width: '25ch'},
                    marginTop: '40px',
                    marginLeft: '40px'
                }}
                noValidate
                autoComplete="off"
            >
                {models.map((model, index) => (
                    <Grid container key={index} alignItems="center" spacing={2}>
                        <Grid item xs={3}>
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
        </div>
    );

}

export default Settings;
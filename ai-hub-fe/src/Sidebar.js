import React from 'react';
import Checkbox from '@mui/material/Checkbox';
import {Link, useLocation} from 'react-router-dom';
import './Sidebar.css';

function Sidebar({selectedModels, handleModelChange}) {
    const location = useLocation();

    const isCurrentPage = (path) => {
        return location.pathname === path;
    };

    const ModelSelector = ({modelId, modelName}) => (
        <div className="model-selector">
            <Checkbox
                id={modelId}
                checked={selectedModels[modelName]}
                onChange={(e) => handleModelChange(modelName, e.target.checked)}
            />
            <label htmlFor={modelId}>{modelName.split('/')[0]} / {modelName.split('/')[1]}</label>
        </div>
    );

    return (
        <div className="sidebar">
            <h1>AI Hub</h1>
            <div className="model-selection-area">
                {isCurrentPage('/') ? (
                    <h3 className="sidebar-section-title">Models</h3>
                ) : (
                    <Link to="/" className="link-style"><h3>Models</h3></Link>
                )}
                {Object.keys(selectedModels).map((modelName, index) => (
                    <ModelSelector key={index} modelId={`model${index}`} modelName={modelName}/>
                ))}
                {isCurrentPage('/settings') ? (
                    <h3>Settings</h3>
                ) : (
                    <Link to="/settings" className="link-style"><h3>Settings</h3></Link>
                )}
                {isCurrentPage('/history') ? (
                    <h3>History</h3>
                ) : (
                    <Link to="/history" className="link-style"><h3>History</h3></Link>
                )}
                {isCurrentPage('/assess') ? (
                    <h3>Assess</h3>
                ) : (
                    <Link to="/assess" className="link-style"><h3>Assess</h3></Link>
                )}
            </div>
        </div>
    );
}

export default Sidebar;
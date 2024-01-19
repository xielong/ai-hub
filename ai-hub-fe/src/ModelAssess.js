import React, {useEffect, useRef} from 'react';
import Chart from 'chart.js/auto';
import './ModelAssess.css';
import Sidebar from "./Sidebar";

const ModelAssess = () => {
    const chartRef = useRef(null);

    const selectedModels = {/* ... */};
    const handleModelChange = {/* ... */};

    useEffect(() => {
        const chartInstance = new Chart(chartRef.current, {
            type: 'bar',
            data: {
                labels: ['ChatGPT4', '文心一言', '混元高级版', 'MiniMax abab6', 'GPT3.5',
                    '百川', '智谱', '混元标准版', 'MiniMax abab5.5', '讯飞星火', '通义千问'],
                datasets: [{
                    label: '翻译能力评估',
                    data: [8, 7.1, 6.6, 6.6, 6.4, 6.4, 6.3, 6.3, 6, 5.9, 5.3],
                    backgroundColor: [
                        'rgba(255, 99, 132, 0.5)',
                        'rgba(54, 162, 235, 0.5)',
                        'rgba(255, 99, 132, 0.5)',
                        'rgba(255, 99, 132, 0.5)',
                        'rgba(54, 162, 235, 0.5)',
                        'rgba(54, 162, 235, 0.5)',
                        'rgba(54, 162, 235, 0.5)',
                        'rgba(54, 162, 235, 0.5)',
                        'rgba(54, 162, 235, 0.5)',
                        'rgba(54, 162, 235, 0.5)',
                        'rgba(54, 162, 235, 0.5)',
                    ]
                }],
            },

            options: {
                scales: {
                    y: {
                        min: 4,
                        max: 9
                    }
                }
            }
        });

        return () => chartInstance.destroy();
    }, []);

    return (
        <div className="main-container">
            <Sidebar selectedModels={selectedModels} handleModelChange={handleModelChange}/>
            <div style={{margin: '20px', width: '80%'}}>
                <canvas ref={chartRef}/>
                <p>备注：红色模型的均价是蓝色模型的 10 倍，选择模型时需要考虑到价格因素</p>
            </div>
        </div>
    );
};

export default ModelAssess;

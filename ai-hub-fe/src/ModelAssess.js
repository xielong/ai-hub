import React, {useEffect, useRef} from 'react';
import Chart from 'chart.js/auto';
import './ModelAssess.css';
import Sidebar from "./Sidebar";

const ModelAssess = () => {
    const translationChartRef = useRef(null);
    const codingChartRef = useRef(null);

    const selectedModels = {/* ... */};
    const handleModelChange = {/* ... */};

    useEffect(() => {
        const translationChartInstance = new Chart(translationChartRef.current, {
            type: 'bar',
            data: {
                labels: ['ChatGPT4', '智谱glm-4', '文心一言ERNIE-Bot 4.0', '通义千问plus',
                    '混元高级版', 'MiniMax abab6', 'GPT3.5', '百川智能',
                    '混元标准版', '智谱chatGLM_turbo', 'MiniMax abab5.5', '讯飞星火',
                    '通义千问turbo', '文心一言ERNIE-Bot-turbo'],
                datasets: [{
                    label: '翻译能力评估',
                    data: [8, 7.5, 7, 7,
                        6.6, 6.6, 6.4, 6.4,
                        6.4, 6.3, 6.1, 5.8,
                        5.3, 5],
                    backgroundColor: [
                        'rgba(255, 99, 132, 0.5)',
                        'rgba(255, 99, 132, 0.5)',
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
                        'rgba(54, 162, 235, 0.5)',
                    ]
                }],
            },

            options: {
                scales: {
                    y: {
                        min: 2,
                        max: 9
                    }
                }
            }
        });

        const codingChartInstance = new Chart(codingChartRef.current, {
            type: 'bar',
            data: {
                labels: ['ChatGPT4', '文心一言ERNIE-Bot 4.0', '智谱glm-4', '混元高级版', '混元标准版', '百川智能', '通义千问plus', 'GPT3.5',
                    'MiniMax abab6', 'MiniMax abab5.5', '文心一言ERNIE-Bot-turbo', '讯飞星火', '智谱chatGLM_turbo', '通义千问turbo'],
                datasets: [{
                    label: '编程能力评估',
                    data: [8.3, 7.6, 7.3, 6.8, 6.6,
                        6.1, 6, 5.8, 5.6, 5.5, 5, 3.9, 3.7, 2.7],
                    backgroundColor: [
                        'rgba(255, 99, 132, 0.5)',
                        'rgba(255, 99, 132, 0.5)',
                        'rgba(255, 99, 132, 0.5)',
                        'rgba(255, 99, 132, 0.5)',
                        'rgba(54, 162, 235, 0.5)',
                        'rgba(54, 162, 235, 0.5)',
                        'rgba(54, 162, 235, 0.5)',
                        'rgba(54, 162, 235, 0.5)',
                        'rgba(255, 99, 132, 0.5)',
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
                        min: 2,
                        max: 9
                    }
                }
            }
        });

        return () => {
            translationChartInstance.destroy();
            codingChartInstance.destroy();
        }
    }, []);

    return (
        <div className="main-container">
            <Sidebar selectedModels={selectedModels} handleModelChange={handleModelChange}/>
            <div className="charts-container">
                <div>
                    <canvas ref={translationChartRef}/>
                    <p>备注：红色模型的均价是蓝色模型的 10 倍，选择模型时需要考虑到价格因素</p>
                </div>
                <div>
                    <canvas ref={codingChartRef}/>
                    <p>备注：红色模型的均价是蓝色模型的 10 倍，选择模型时需要考虑到价格因素</p>
                </div>
            </div>
        </div>
    );
};

export default ModelAssess;

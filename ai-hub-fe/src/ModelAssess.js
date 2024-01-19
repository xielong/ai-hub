import React, {useEffect, useRef} from 'react';
import Chart from 'chart.js/auto';
import './ModelAssess.css';

const ModelAssess = () => {
    const chartRef = useRef(null);

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

    return <canvas ref={chartRef}/>;
};

export default ModelAssess;

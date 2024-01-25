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
        let translationChartInstance;
        let codingChartInstance;

        const createChart = (chartRef, apiUrl, chartLabel) => {
            fetch(apiUrl)
                .then(response => response.json())
                .then(data => {
                    const labels = data.map(item => item.modelAlias);
                    const ratings = data.map(item => item.rating / 10);
                    const backgroundColors = data.map(item =>
                        item.modelVersion >= 10 ? 'rgba(255, 99, 132, 0.5)' : 'rgba(54, 162, 235, 0.5)'
                    );

                    return new Chart(chartRef.current, {
                        type: 'bar',
                        data: {
                            labels: labels,
                            datasets: [{
                                label: chartLabel,
                                data: ratings,
                                backgroundColor: backgroundColors
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
                })
                .catch(error => {
                    console.error(`Error fetching data from ${apiUrl}: `, error);
                });
        };

        // Create translation chart
        translationChartInstance = createChart(translationChartRef, '/api/v1/evaluation/translation', '翻译能力评估');

        // Create coding chart
        codingChartInstance = createChart(codingChartRef, '/api/v1/evaluation/coding', '编程能力评估');

        return () => {
            if (translationChartInstance) translationChartInstance.destroy();
            if (codingChartInstance) codingChartInstance.destroy();
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

$(document).ready(function() {

    $.get('/learningCurve', function(data, status){
        console.log(data);
        console.log(status);

        var ctx = document.getElementById("cost-chart").getContext('2d');
        window.costConfig = {
            type: 'line',
            data: {
                datasets: [{
                    label: 'cost data',
                    data: data
                }]
            },

            options: {
                scales: {
                    yAxes: [{
                        ticks: {
                            beginAtZero:true,
                            steps: 25,
                            stepValue: 0.04,
                            max: 0.1 //max value for the chart is 60
                        }
                    }],
                    xAxes: [{
                        type: 'linear',
                        position: 'bottom',
                        ticks: {
                            beginAtZero:true,
                            steps: 50,
                            stepValue: 2,
                            max: 100 //max value for the chart is 60
                        }
                    }]
                }
            }
        };

        window.costChart = new Chart(ctx, window.costConfig);
    });
});

function getCurveData() {
    $.get('/learningCurve', function(data, status){
        console.log(status);

        window.costConfig.data.datasets.push({
        label: "another cost chart",
        data: data
        });

        window.costChart.update();
    });
}

function startTrain() {
    var alpha = $('#alpha-input').val();
    var count = $('#count-input').val();

    $.post("/train?count=" + count + "&alpha=" + alpha, function(data, status){
        console.log('Train status: ', status);
        alert('Training finished!');

        getCurveData();
    });


}
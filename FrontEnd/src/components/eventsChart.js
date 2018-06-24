import {
    Bar,
    mixins
} from 'vue-chartjs'
const {
    reactiveProp
} = mixins

export default {
    extends: Bar,
    mixins: [reactiveProp],
    props: ['options'],
    mounted() {
        // this.chartData is created in the mixin.
        // If you want to pass options please create a local options object
        this.renderChart(this.chartData, {
            onClick: function(event) {
                var activePoints = this.getElementAtEvent(event)
                var firstPoint = activePoints[0];
                if (firstPoint !== undefined) {
                    var label = this.data.labels[firstPoint._index];
                    var value = this.data.datasets[firstPoint._datasetIndex].data[firstPoint._index];
                    var loc = "eventGraph?id=" + value.id;
                    window.location = loc;
                }
            },
            responsive: true,
            maintainAspectRatio: false,
            fontColor: '#FFFFFF',
            legend: {
                labels: {
                    // This more specific font property overrides the global property
                    fontColor: 'black',
                    fontSize: 20
                }
            },
            tooltips: {
                enabled: true,
                mode: 'single',
                callbacks: {
                    title: function(tooltipItems, data) {
                        var evnt = data.datasets[0].data[tooltipItems[0].index].label;
                        return evnt
                    },
                    label: function(tooltipItems, data) {
                        function restHeartStats(rate, age) {
                            if (age <= 35) {
                                if (rate <= 45) {
                                    return "Your heart rate is dangerously low, we recommend you consult a doctor immediately!"
                                }
                                if (rate <= 61) {
                                    return "Normal activity, heart rate indicates you're in excellent shape."
                                }
                                if (rate <= 70) {
                                    return "Normal activity, heart rate indicates you're in above average shape."
                                }
                                if (rate <= 90) {
                                    return "Normal activity, heart rate indicates you're in average shape."
                                }
                                if (rate <= 100) {
                                    return "Your heart rate should be lower, activity might be too stressful or you need to be in better shape in case you're seeing this message consistently."
                                }
                                return "Heart rate is dangerously high, we recommend you eliminate this activity as soon as possible."
                            }
                            if (age >= 36 && age <= 55) {
                                if (rate <= 49) {
                                    return "Your heart rate is dangerously low, we recommend you consult a doctor immediately!"
                                }
                                if (rate <= 63) {
                                    return "Normal activity, heart rate indicates you're in excellent shape."
                                }
                                if (rate <= 72) {
                                    return "Normal activity, heart rate indicates you're in above average shape."
                                }
                                if (rate <= 77) {
                                    return "Normal activity, heart rate indicates you're in average shape."
                                }
                                if (rate <= 83) {
                                    return "Your heart rate should be lower, activity might be too stressful or you need to be in better shape in case you're seeing this message consistently."
                                }
                                return "Heart rate is dangerously high, we recommend you eliminate this activity as soon as possible."
                            }
                            if (age >= 56) {
                                if (rate <= 50) {
                                    return "Your heart rate is dangerously low, we recommend you consult a doctor immediately!"
                                }
                                if (rate <= 66) {
                                    return "Normal activity, heart rate indicates you're in excellent shape."
                                }
                                if (rate <= 74) {
                                    return "Normal activity, heart rate indicates you're in above average shape."
                                }
                                if (rate <= 78) {
                                    return "Normal activity, heart rate indicates you're in average shape."
                                }
                                if (rate <= 84) {
                                    return "Your heart rate should be lower, activity might be too stressful or you need to be in better shape in case you're seeing this message consistently."
                                }
                                return "Heart rate is dangerously high, we recommend you eliminate this activity as soon as possible."
                            }
                        };

                        function sportHeartStats(rate, age) {
                            var maxRate = 220 - age;
                            if (rate < 0.5 * maxRate) {
                                return "Heart rate is too low, we recommend you to be more active in this session."
                            }
                            if (rate >= 0.5 * maxRate && rate < 0.6 * maxRate) {
                                return "This activity is great is recommended to do often to generally stay in shape."
                            }
                            if (rate >= 0.6 * maxRate && rate < 0.7 * maxRate) {
                                return "This activity is great forburning fat with preserving as much muscle mass as possible."
                            }
                            if (rate >= 0.7 * maxRate && rate < 0.8 * maxRate) {
                                return "This activity is great improving aerobic and cardio fitness,\n ideal for increasing endurance over long distances."
                            }
                            if (rate >= 0.8 * maxRate && rate < 0.9 * maxRate) {
                                return "This activity is great improving anaerobic fitness and muscle strength,\n ideal if you are trying to build muscle."
                            }
                            return "This activity is great improving maximum performance and speed,\n ideal for short bursts of intense activity and shouldn't be done over a long period of time."

                        };

                        function heartStats(type, rate, age) {
                            if (type == "Rest") {
                                return restHeartStats(rate, age);
                            }
                            return sportHeartStats(rate, age);
                        };
                        var avg = 'Average heart rate: ' + [tooltipItems.yLabel];
                        var evnt = 'Type: ' + data.datasets[0].data[tooltipItems.index].tag;
                        var stats = heartStats(data.datasets[0].data[tooltipItems.index].tag, [tooltipItems.yLabel], 30);
                        return [avg, evnt, stats];
                    }
                }
            }

        })
    }
}

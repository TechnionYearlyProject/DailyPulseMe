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
                            var avg = 'HRV value: ' + [tooltipItems.yLabel];
                            return avg;
                        }
                    }
                }
            }


        )
    }
}

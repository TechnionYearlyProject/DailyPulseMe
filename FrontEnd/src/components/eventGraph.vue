<!--
In this page we present the user's graph of a specific event,
with the data being brought from the back.
-->
<template>
  <b-container style="margin-top:30px;width:60%;" fluid>
      <b-card style="z-index:-2; background: white; position:absolute; opacity:0.5;
  width:60%; height:520px; margin-left:-15px; margin-top:-10px;">
</b-card>
    <line-chart :chart-data="datacollection" style="z-index:1 position:fixed;height:500px;"></line-chart>
        <Spinner size="massive" v-if="!timeup" style="z-index:1; margin-top:-500px;"></Spinner>

  </b-container>
</template>
<script>
    import Spinner from 'vue-simple-spinner'
    import LineChart from './singleChart.js'
    export default {
        name: 'Event',
        components: {
            LineChart,
            Spinner
        },
        data() {
            return {
                datacollection: null,
                datesList: [],
                avgList: [],
                timeup: false
            }
        },
        mounted() {
            this.fillData()
        },

        methods: {
            fillData() {
                var name = ['']
                var id = this.$route.query.id;
                var aList = []
                var dList = []
                this.$http.post('https://webapp-180506135919.azurewebsites.net/users/getEvent', {
                    "id": id
                }, {
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': localStorage.getItem('token'),
                    }
                }).then((res) => {
                    name[0] = res.body.name
                    var pulsesArr = res.body.pulses;
                    var numofpulses = pulsesArr.length;
                    var startTime = parseInt(res.body.startTime) + 60000
                    for (var i = 0; i < numofpulses; i++) {
                        var date = new Date(startTime)
                        var hours = date.getHours()
                        hours = ("0" + hours).slice(-2);
                        var minutes = date.getMinutes()
                        minutes = ("0" + minutes).slice(-2);
                        var str = hours + ":" + minutes;
                        dList.push(str);
                        startTime = startTime + 60000;
                    }
                    for (var i = 0; i < numofpulses; i++) {
                        var pulse = pulsesArr[i];
                        aList.push({
                            y: pulse.value
                        });
                    }
                    this.datacollection = {
                        labels: dList,
                        datasets: [{
                            label: name[0],
                            backgroundColor: '#007afd',
                            growDuration: 10,
                            data: aList
                        }]
                    }
                    this.timeup = true
                })

            },
            getRandomInt() {
                return Math.floor(Math.random() * (50 - 5 + 1)) + 5
            }
        }
    }
</script>

<!--
This is a wrapper for the events graph page.
We use it for visual design and the spinner that appears till the page loads.
-->
<template>
  <b-container style="margin-top:30px;width:60%;">
  		<b-card style="z-index:-2; background: #00000E; position:absolute; opacity:0.3;
  width:60%; height:420px; margin-left:-15px; margin-top:-10px;">
</b-card>
<eventsGraph v-if="!isempty"  style="z-index:1; height:380px; width:50%;"></eventsGraph>
    <Spinner size="massive" v-if="!timeup" style="z-index:1; margin-top:30px;">Loading..</Spinner>
  </b-container>
</template>

<script>
    import eventsGraph from './eventsGraph'
    import Spinner from 'vue-simple-spinner'
    export default {
        name: 'eventsWrapper',
        components: {
            eventsGraph,
            Spinner
        },
        created: function() {
            this.getEvents();
        },
        data() {
            return {
                isempty: true,
                timeup: false
            }
        },
        methods: {
            onCarouselUpdate() {
                console.log('Carousel Updated!')
            },
            getEvents() {
                this.$http.post('https://webapp-180506135919.azurewebsites.net/users/getAllEventsWhichHavePulses', {
                    "first": 1515103200000,
                    "second": new Date().getTime()
                }, {
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': localStorage.getItem('token'),
                    }
                }).then((res) => {
                    var eventsArr = res.body;
                    var arrayLength = eventsArr.length;
                    if (arrayLength == 0) console.log('No events!')
                    this.isempty = false
                    this.timeup = true
                })
            }
        }
    }
</script>

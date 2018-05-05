
<template>
    <div id="app">
        <calendar
                :first-day="1"
                :all-events="events"
                :canAddEvent="true"
                :canDeleteEvent="true"
                @eventAdded="eventAdded"
                @eventDeleted="eventDeleted"
        ></calendar>
    </div>
</template>

<script>
    import {Calendar} from 'vue-bootstrap4-calendar';
    export default {
        name: 'app',
        created: function () {
          this.getEvents();
              },
        data() {
            return {
                datesList : [],
                avgList: [],
                events: []
            }
        },
        components: {
            Calendar
        },
        methods: {
            eventAdded(event) {
                this.events.push(event);
            },
            eventDeleted(event) {
                this.events.splice(this.events.indexOf(event), 1);
            },
            getEvents () {
                 this.$http.post('http://localhost:8081/users/getEvents',{
                   "first": 1515103200000,
                   "second": 1516399200000
                 }
                  ,{headers: {'Content-Type': 'application/json',
                   'Authorization': localStorage.getItem('token'),}
                 }).then((res) => {
                   // res.body = array of event object
                   var eventsArr = res.body;
                     var arrayLength = eventsArr.length;
                     for (var i = 0; i < arrayLength; i++) {
                       var date = new Date(parseInt(eventsArr[i].startTime))
                       var day = date.getDate()
                       var month =  date.getMonth()
                       var year = date.getFullYear()
                       var hours = date.getHours()
                       hours = ("0" + hours).slice(-2);
                       var minutes = date.getMinutes()
                       minutes = ("0" + minutes).slice(-2);
                       var str = day;
                       var str = day + "." + (month + 1) + "." + year +" - " + hours + ":" + minutes
                         this.datesList.push(str);
                     }
                     for (var i = 0; i < arrayLength; i++) {
                         var evnt = eventsArr[i];
                       this.avgList.push({label: evnt.name,y: evnt.pulseAverage ,tag: evnt.tag, id: evnt.id });
                     }
                 })
             }
        },
        mounted() {
            let me = this;
            this.getEvents();
            setTimeout(function () {
                for (var i = 0; i < me.datesList.length; i++) {
                  me.events.push(                {
                                      id: 1,
                                      title:'Event 1',
                                      description: 'http://localhost:8080/eventGraph?id=' + me.avgList[i].id,
                                      color: 'card-danger card-inverse',
                                      date: me.datesList[i]
                                  });
                    i++;
                }
                for (var variable in me.datesList) {
                }
                me.events = [ // you can make ajax call here,
                ];
            }, 1000);
        }
    }
</script>

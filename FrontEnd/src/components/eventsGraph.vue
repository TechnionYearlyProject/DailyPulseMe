
 <template>
  <b-container style="margin-top:30px;width:60%;" fluid>
      <b-card style="z-index:-2; background: #FFFFFF; position:absolute; opacity:0.5;
  width:60%; height:520px; margin-left:-15px; margin-top:-10px;">
</b-card>
    <line-chart :chart-data="datacollection" style="z-index:1 position:fixed;height:500px;"></line-chart>
        <Spinner size="massive" v-if="!timeup" style="z-index:1; margin-top:-500px;"></Spinner>

  </b-container>
</template>
<script>
  import Spinner from 'vue-simple-spinner'
  import LineChart from './eventsChart.js'
  export default {
    name:'eventsGraphs',
    components: {
      LineChart,
      Spinner
    },
    data () {
      return {
        datacollection: null,
        datesList : [],
        avgList: [],
        timeup: false
      }
    },
    mounted () {
      this.fillData()
    },

    methods: {
      fillData () {
        var aList =[]
        var dList = []
         this.$http.post('https://webapp-180506135919.azurewebsites.net/users/getAllEventsWhichHavePulses',{
             "first": 1515103200000,
             "second": new Date().getTime()
           }
            ,{headers: {'Content-Type': 'application/json',
             'Authorization': localStorage.getItem('token'),}
           }).then((res) => {

             var eventsArr = res.body;
             var arrayLength = eventsArr.length;
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
                   dList.push(str);
               }
               for (var i = 0; i < arrayLength; i++) {
                   var evnt = eventsArr[i];
               if(evnt.tag == null){
                 evnt.tag = "Rest";
               }
                 aList.push({label: evnt.name,y: evnt.pulseAverage ,tag: evnt.tag, id: evnt.id });
               }
               this.datacollection = {
          labels: dList,
          datasets: [
            {
              label: 'Events',
              gradient : "['#ffbe88', '#ff93df']",
              backgroundColor: "#800517",
              growDuration: 10,
              data: aList
          }]
        }
        this.timeup = true
           })
        
      },
      getRandomInt () {
        return Math.floor(Math.random() * (50 - 5 + 1)) + 5
      }
    }
}
</script>

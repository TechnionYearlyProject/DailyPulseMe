
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
  import LineChart from './hrvChart.js'
  export default {
    name:'HRVGraph',
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
       HRVStats(type, value){
      if(type != "Rest"){
        return "";
      }
      if(value < 0.3){
        return "Your HRV value indicates you are quite calm, this is wonderful!"
      }
      if(value < 0.5){
        return "Your HRV value indicates your sterss level is not zero but it isnt very high either"
      }
      if(value < 0.7){
        return "Your HRV value indicates your sterss level is medium, we recomend you try to avoid such activities in the future"
      }
      return "Your HRV value indicates your sterss level is high, this is bad for your health, we strongly recomend you to avoid such activities in the future"
      },
      fillData () {
 var id = this.$route.query.id;
   var name = ['']
        var aList =[]
        var dList = []
      var ret = [];
      var sum = [];
      sum[0] = 0;
      var type = [];
            this.$http.post('https://webapp-180506135919.azurewebsites.net/users/getEvent',{"id": id}
             ,{headers: {'Content-Type': 'application/json',
              'Authorization': localStorage.getItem('token'),}
            }).then((res) => {
              // res.body = array of event object
        type.push({y: String(res.body.tag)});
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
                for (var i = 0; i < numofpulses-1; i++) {
                    var pulse = pulsesArr[i];
                  aList.push({y: Math.abs(pulsesArr[i+1].value - pulse.value)/60});
                  sum[0] += aList[i].y;
                }
        aList.push({y: Math.abs((pulsesArr[numofpulses-1].value - pulsesArr[numofpulses-2].value)/60)});
        sum[0] += Math.abs((pulsesArr[numofpulses-1].value - pulsesArr[numofpulses-2].value)/60);
        sum[0] = sum[0] / numofpulses;
      var avg = sum[0];
       if(type[0].y == null){
         type[0].y = "Rest";
       }
      name[0] += this.HRVStats(type[0].y, avg);
      this.datacollection = {
          labels: dList,
          datasets: [
            {
              label: name,
              backgroundColor: '#007afd',
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

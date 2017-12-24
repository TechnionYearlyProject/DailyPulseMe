<script>
import {Line} from 'vue-chartjs'

export default {  
  extends: Line,
  data () {
    return {
      gradient: null,
      gradient2: null
    }
  },
  mounted () {
    this.gradient = this.$refs.canvas.getContext('2d').createLinearGradient(0, 0, 0, 450)
    this.gradient2 = this.$refs.canvas.getContext('2d').createLinearGradient(0, 0, 0, 450)

    this.gradient.addColorStop(0, 'rgba(255, 0,0, 0.5)')
    this.gradient.addColorStop(0.5, 'rgba(255, 0, 0, 0.25)');
    this.gradient.addColorStop(1, 'rgba(255, 0, 0, 0)');
    
    this.gradient2.addColorStop(0, 'rgba(0, 231, 255, 0.9)')
    this.gradient2.addColorStop(0.5, 'rgba(0, 231, 255, 0.35)');
    this.gradient2.addColorStop(1, 'rgba(0, 231, 255, 0)');


    this.renderChart({
      labels: ['16/12', '17/12', '18/12', '19/12', '20/12', '21/12', '22/12', '23/12'],
      datasets: [
        {
          label: 'Average heart rate per day',
          borderColor: '#05CBE1',
          pointBackgroundColor: 'white',
          pointBorderColor: 'white',
          borderWidth: 2,
          backgroundColor: this.gradient2,
          data: [60, 55, 125, 65, 95, 60 ,64,123]
         }
        // ,{
         
        //   label: 'Eran',
        //   borderColor: '#FC2525',
        //   pointBackgroundColor: 'white',
        //   borderWidth: 2,
        //   pointBorderColor: 'white',
        //   backgroundColor: this.gradient,
        //   data: [65, 75, 50, 125, 65, 60, 65]
        // }
      ]
    }, {responsive: true, maintainAspectRatio: false,fontColor: '#66226'})
}
}
</script>

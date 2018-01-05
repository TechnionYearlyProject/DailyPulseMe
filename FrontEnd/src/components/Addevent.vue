<template>
  <div style="width:50%; margin:auto; margin-top: 20px;">
    <b-form @submit.prevent="addevent">
      <b-form-group id="exampleInputGroup1"
                    label="Event Name:"
                    label-for="exampleInput1">
        <b-form-input v-model="input.name"
                      type="text"
                      required
                      placeholder="Enter name">
        </b-form-input>
      </b-form-group>
      <b-form-group label="Date:"
                    label-for="exampleInput1">
        <b-form-input v-model="date"
                      type="date"
                      required
                      placeholder="Enter date">
        </b-form-input>
        {{this.date}}
      </b-form-group>
      <b-form-group label="Start time:"
                    label-for="exampleInput1">
        <b-form-input v-model="starttime"
                      type="time"
                      required
                      placeholder="Enter start">
        </b-form-input>
      </b-form-group>
      {{this.starttime}}

      <b-form-group label="End time:"
                    label-for="exampleInput1">
        <b-form-input v-model="endtime"
                      type="time"
                      required
                      placeholder="Enter end">
        </b-form-input>
      </b-form-group>
      {{this.endtime}}

      <b-form-group label="Description:"
                    label-for="exampleInput2">
        <b-form-input v-model="input.description"
                      type="text"
                      required
                      placeholder="Enter description">
        </b-form-input>
      </b-form-group>
      <b-form-group id="exampleInputGroup3"
                    label="Tag:"
                    label-for="exampleInput3">
        <b-form-input v-model="input.tag"
                      type="text"
                      required
                      placeholder="Enter tag">
        </b-form-input>
      </b-form-group>
      <b-button type="submit" variant="primary">Submit</b-button>
      <b-button type="reset" variant="danger">Reset</b-button>
    </b-form>
  </div>
</template>
<script type="application/json">
  export default{
    name: 'Event',
    data () {
      return {
        date: '',
        starttime: '',
        endtime: '',
        input: {
          name: '',
          start: this.date + 'D' + this.starttime,
          end: this.date + 'D' + this.endtime,
          description: '',
          tag: ''
        }
      }
    },
    methods: {
      addevent() {
        let url = 'http://localhost:8081/users/addEvent'
        this.$http.post(url, this.input,{headers: {'Content-Type': 'application/json',
          'Authorization': localStorage.getItem('token')}} ).then((res) => {
          console.log('Success', res);
          //this.$router.push('/');
        }, (err) => {
          console.log('Error: ', err)
        })
      }
    }
  }
</script>

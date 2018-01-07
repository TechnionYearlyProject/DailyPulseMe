<template>
  <div style="width:50%; margin:auto; margin-top: 20px;">
    <b-form @submit.prevent="addevent">
      <b-form-group id="exampleInputGroup1"
                    label="Event Name:"
                    label-for="exampleInput1">
        <b-form-input v-model="name"
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
        <b-form-input v-model="description"
                      type="text"
                      required
                      placeholder="Enter description">
        </b-form-input>
      </b-form-group>
      <b-form-group id="exampleInputGroup3"
                    label="Tag:"
                    label-for="exampleInput3">
        <b-form-input v-model="tag"
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
        name: '',
        description: '',
        tag: ''
      }
    },
    methods: {
      addevent() {
        var sta = (this.date + ' ' + this.starttime);
        var en = (this.date + ' ' + this.endtime);
        let input = {"name": this.name, "start": sta,"end": en, "description": this.description, "tag": this.tag};
        window.alert(input["start"]);
        input = JSON.stringify(input);
        let url = 'http://localhost:8081/users/addEvent'
        this.$http.post(url, input,{headers: {'Content-Type': 'application/json',
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

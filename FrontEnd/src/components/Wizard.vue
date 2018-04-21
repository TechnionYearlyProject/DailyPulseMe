<template>
  <div style="width:50%;margin: auto;">
  <form-wizard @on-complete="onComplete" 
                        shape="square"
                        color="#3498db"
                        title="DailyPulse"
                        subtitle="A few steps are required">
      <tab-content title="Fitness Band Account"
                   icon="ti-user">
                   <b-btn v-b-toggle.collapse1 variant="success" v-on:click="microsoftfit">Microsoft</b-btn>
    <b-btn v-b-toggle.collapse1 variant="warning" v-on:click="googlefit" >Google</b-btn>
      </tab-content>
      <tab-content title="Calendar Account"
                   icon="ti-settings">
        <b-btn v-b-toggle.collapse1 variant="success" >Fitbit</b-btn>
    <b-btn v-b-toggle.collapse1 variant="warning" >Google Fit</b-btn>
      </tab-content>
      <tab-content title="Last step"
                   icon="ti-check">
        Yuhuuu! This seems pretty damn simple
      </tab-content>
  </form-wizard>
</div>
</template>
<script src="https://unpkg.com/vue-form-wizard/dist/vue-form-wizard.js"></script>

<script>
  import Connect from './Connect'
export default {
  components:{ Connect },
    name:'Wizard',
     methods: {
      onComplete: function(){
          alert('Yay. Done!');
       }
      }
  }
</script>
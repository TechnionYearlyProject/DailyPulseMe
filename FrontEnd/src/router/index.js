import Vue from 'vue'
import Router from 'vue-router'
import Home from '../components/Home'
import Login from '../components/Login'
import Register from '../components/register'
import Config from '../components/Config'
import Addevent from '../components/Addevent'
import eventsWrapper from '../components/eventsWrapper'
import Event from '../components/eventGraph'
import GoogleFit from '../components/GoogleFit'
import MicrosoftFit from '../components/MicrosoftFit'
import RemoveEvent from '../components/RemoveEvent'
import Calendar from '../components/Calendar'
import Wizard from '../components/Wizard'

const requireAuth = (to, from, next) => {
 checkToken(function(){
            next()

    },function(){
               router.push('/login');

    });
}
const loginRedirect = (to, from, next) => {
   checkToken(function(){
    router.push('/wizard');
    },function(){
    next()
    });
}


Vue.use(Router)
function checkToken(funcYes,funcNo){
   var self = this;
    Vue.http.get('http://localhost:8081/users/authenticateToken',{headers: {'Content-Type': 'application/json',
      'Authorization': localStorage.getItem('token')}
         }).then((res) => {
          funcYes()
            }, (err) => {
          funcNo()
          })
    }
function account(){
      this.$http.get('http://localhost:8081/users/isThereOneCalendar',{headers: {'Content-Type': 'application/json',
  'Authorization': localStorage.getItem('token')}
     }).then((res) => {
          this.isAccount = true
        })
    }
const router = new Router({
  mode: 'history',
  routes: [
    {
      path: '/login',
      name: 'Login',
      component: Login,
      beforeEnter: loginRedirect
    },
    {
      path: '/register',
      name: 'Register',
      component: Register
    },
    {
      path: '/eventsGraph',
      name: 'eventsWrapper',
      component: eventsWrapper,
      beforeEnter: requireAuth
    },
    {
      path: '/eventGraph',
      name: 'Event',
      component: Event,
      beforeEnter: requireAuth
    },
    {
      path: '/',
      alias: '/home',
      name: 'Home',
      component: Home,
      // beforeEnter: requireAuth
    },
    {
      path: '/calendar',
      name: 'Calendar',
      component: Calendar,
      beforeEnter: requireAuth
    },
    {
      path: '/Wizard',
      name: 'Wizard',
      component: Wizard,
      beforeEnter: requireAuth
    },
     {
      path: '/config',
      name: 'Config',
      component: Config,
      beforeEnter: requireAuth
    },
    {
     path: '/addevent',
     name: 'Addevent',
     component: Addevent,
     beforeEnter: requireAuth
   },{
      path: '/token',
      name: 'Token',
      component: GoogleFit,
      beforeEnter: requireAuth
    },{
      path: '/token1',
      name: 'Token1',
      component: MicrosoftFit,
      beforeEnter: requireAuth
    },
    {
     path: '/removeevent',
     name: 'RemoveEvent',
     component: RemoveEvent,
     beforeEnter: requireAuth
   },

  ]
})
router.beforeEach((to, from, next) => {
  document.title = 'DailyPulse'
  next()
})
export default router

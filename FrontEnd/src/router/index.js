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
import GoogleAuth from '../components/GoogleAuth'
import MicrosoftFit from '../components/MicrosoftFit'
import RemoveEvent from '../components/RemoveEvent'
import Calendar from '../components/Calendar'
import Wizard from '../components/Wizard'
import HRVGraph from '../components/HRVGraph'

function checkAccount(push1,push2){
         var self = this;
    Vue.http.get('http://localhost:8081/users/isConnectedToGoogleCalendar',{headers: {'Content-Type': 'application/json',
      'Authorization': localStorage.getItem('token')}
         }).then((res) => {
              if(res.body == false)
                push2()
              else
                push1()
            })
        }
const requireAuth = (to, from, next) => {
 checkToken(function(){
            next()

    },function(){
               router.push('/login');

    });
}
const checkWizard = (to, from, next) => {
 checkAccount(function(){
            next()

    },function(){
               router.push('/wizard');

    });
}
const loginRedirect = (to, from, next) => {
   checkToken(function(){
    router.push('/wizard');
    },function(){
    next()
    });
}
const loginRedirectToWizard = (to, from, next) => {
   checkAccount(function(){
    router.push('/wizard');
    },function(){
    router.push('/');
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
      beforeEnter: checkWizard,
      beforeEnter: requireAuth
    },
    {
      path: '/HRVGraph',
      name: 'HRVGraph',
      component: HRVGraph,
      beforeEnter: checkWizard,
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
    },
    {
      path: '/calendar',
      name: 'Calendar',
      component: Calendar,
      beforeEnter: checkWizard,
      beforeEnter: requireAuth
    },
    {
      path: '/Wizard',
      name: 'Wizard',
      component: Wizard,
      beforeEnter: requireAuth
    },
     {
      path: '/settings',
      name: 'Config',
      component: Config,
      beforeEnter: checkWizard,
      beforeEnter: requireAuth
    },
    {
     path: '/addevent',
     name: 'Addevent',
     component: Addevent,
     beforeEnter: requireAuth
   },{
      path: '/googleauth',
      name: 'GoogleAuth',
      component: GoogleAuth,
      beforeEnter: loginRedirect
    },
    {
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
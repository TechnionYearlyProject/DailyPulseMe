import Vue from 'vue'
import Router from 'vue-router'
import Home from '../components/Home'
import Login from '../components/Login'
import Register from '../components/register'
import Profile from '../components/Profile'
import Addevent from '../components/Addevent'

const requireAuth = (to, from, next) => {
 checkToken(function(){
            next()

    },function(){
               router.push('/login');

    });
}
const loginRedirect = (to, from, next) => {
   checkToken(function(){
    router.push('/');
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
      path: '/',
      alias: '/home',
      name: 'Home',
      component: Home,
      beforeEnter: requireAuth
    },
     {
      path: '/profile',
      name: 'Profile',
      component: Profile,
      beforeEnter: requireAuth
    },
    {
     path: '/addevent',
     name: 'Addevent',
     component: Addevent,
     beforeEnter: requireAuth
   }
  ]
})
router.beforeEach((to, from, next) => {
  document.title = 'DailyPulse'
  next()
})
export default router

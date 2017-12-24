                           import Vue from 'vue'
import Router from 'vue-router'
import Home from '../components/HelloWorld'
import Login from '../components/Login'
import Register from '../components/register'

const hasToken = (to, from, next) => {
  const token = localStorage.getItem('JWT')
  const username = localStorage.getItem('username')
  if (token) {
    store.commit(types.LOGIN_SUCCESS, { token, username })
    router.push('/home')
  } else {
    next()
  }
}
//store.getters.isLoggedIn
const requireAuth = (to, from, next) => {
  if (!(localStorage.getItem('token')) || localStorage.getItem('token') == 'false' ) {
    router.push('/');
  } else {
    router.push('/home');
  }
}

Vue.use(Router)

const router = new Router({
  routes: [
    {
      path: '/',
      alias: '/login',
      name: 'Login',
      component: Login
    },
    {
      path: '/register',
      name: 'Register',
      component: Register
    },
    {
      path: '/home',
      name: 'Home',
      component: Home,
      beforeEnter: requireAuth
    }
  ]
})

export default router

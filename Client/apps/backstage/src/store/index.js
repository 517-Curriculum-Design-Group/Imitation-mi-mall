import { createStore } from 'vuex'
const store = createStore({
    state () {
      return {
        user:{}
        //用户类型
      }
    },
    mutations: {
        SET_USERTYPE(state,user){
            state.user = user
        }
    }
  })

  export default store
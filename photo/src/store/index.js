import { createStore } from 'vuex'
import createPersistedState from 'vuex-persistedstate';
export default createStore({
  state: {
    currentUser: null, // 存储当前登录用户
  },
  getters: {
  },
  mutations: {
    setCurrentUser(state, user) {
      state.currentUser = user;
    },
    clearCurrentUser(state) {
      state.currentUser = null;
    },
  },
  actions: {
    login({ commit }, user) {
      // 执行登录逻辑
      commit('setCurrentUser', user);
    },
    logout({ commit }) {
      commit('clearCurrentUser');
    },
  },
  modules: {
  },
  plugins: [createPersistedState()], // 添加插件
}) 

<template>
    <div class="login-container">
      <h2>登录</h2>
      <form @submit.prevent="handleLogin">
        <div class="form-group">
          <label for="username">用户名:</label>
          <input type="text" id="username" v-model="username" required />
        </div>
        <div class="form-group">
          <label for="password">密码:</label>
          <input type="password" id="password" v-model="password" required />
        </div>
        <div class="button-group">
          <button type="submit">登录</button>
          <button type="button" @click="goToRegister">注册</button>
          <div v-if="currentUser">{{ currentUser.name }}</div>
        </div>
      </form>
      <div v-if="errorMessage" class="error">{{ errorMessage }}</div>
      <div v-if="successMessage" class="success">{{ successMessage }}</div>
    </div>
  </template>
  
  <script>
  import axios from 'axios';
  import { mapState } from 'vuex'; // 引入 Vuex 的 mapState
  export default {
    computed: {
      ...mapState(['currentUser']), // 映射 Vuex 中的 currentUser
    },
    data() {
      return {
        username: '',
        password: '',
        errorMessage: '',
        successMessage: ''
      };
    },
    methods: {
      handleLogin() {
        if (this.username && this.password) {
            const requestBody = {
                name: this.username,
                password: this.password,
            };

            axios.post('http://localhost:8085/user/login', requestBody)
                .then((response) => {
                    if (response.data.code === 1) {
                        this.successMessage = '登录成功！';
                        this.$store.dispatch('login', response.data.data); // 登录成功后存储用户信息
                        this.errorMessage = '';
                        this.goToHome();
                        // 处理成功后的数据，例如保存用户信息
                    } else {
                        this.errorMessage = response.data.msg || '登录失败';
                        this.successMessage = '';
                    }
                })
                .catch((error) => {
                    this.errorMessage = '登录失败，请检查用户名和密码';
                    this.successMessage = '';
                    console.error(error);
                });
        } else {
            this.errorMessage = '请填写所有字段';
            this.successMessage = '';
        }
    },
      goToRegister() {
        // 跳转到注册页面的逻辑
        this.$router.push('/register'); // 假设你使用了 vue-router
      },
      goToHome(){
        this.$router.push('/home');  // 跳转到主页
      }

    }
  };
  </script>
  
  <style scoped>
  .login-container {
    max-width: 300px;
    margin: 50px auto;
    padding: 20px;
    border: 1px solid #ccc;
    border-radius: 5px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  }
  
  .form-group {
    margin-bottom: 15px;
    display: flex;
    align-items: center; /* 垂直居中对齐 */
  }
  
  label {
    flex: 1; /* 占据一部分宽度 */
    margin-right: 10px; /* 右侧间距 */
  }
  
  input {
    flex: 2; /* 占据较大宽度 */
    padding: 8px;
    border: 1px solid #ccc;
    border-radius: 4px;
  }
  
  .button-group {
    display: flex;
    justify-content: space-between; /* 两个按钮之间的空间 */
  }
  
  button {
    padding: 10px;
    background-color: #007bff;
    border: none;
    color: white;
    border-radius: 4px;
    cursor: pointer;
    flex: 1; /* 两个按钮均分宽度 */
    margin-right: 5px; /* 右侧间距 */
  }
  
  button:last-child {
    margin-right: 0; /* 去掉最后一个按钮的右侧间距 */
  }
  
  button:hover {
    background-color: #0056b3;
  }
  
  .error {
    color: red;
    margin-top: 10px;
  }
  
  .success {
    color: green;
    margin-top: 10px;
  }
  </style>
  
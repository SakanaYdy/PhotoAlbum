<template>
    <div class="register-container">
      <h2>注册</h2>
      <form @submit.prevent="handleRegister">
        <div class="form-group">
          <label for="username">用户名:</label>
          <input type="text" id="username" v-model="username" required />
        </div>
        <!-- <div class="form-group">
          <label for="email">邮箱:</label>
          <input type="email" id="email" v-model="email" required />
        </div> -->
        <div class="form-group">
          <label for="password">密码:</label>
          <input type="password" id="password" v-model="password" required />
        </div>
        <div class="form-group">
          <label for="confirmPassword">确认密码:</label>
          <input type="password" id="confirmPassword" v-model="confirmPassword" required />
        </div>
        <div class="button-group">
          <button type="submit" @click="backToLogin">返回登录</button>
          <button type="button" @click="handleRegister">注册</button>
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
    data() {
      return {
        username: '',
        email: '',
        password: '',
        confirmPassword: '',
        errorMessage: '',
        successMessage: ''
      };
    },
    computed: {
      ...mapState(['currentUser']), // 映射 Vuex 中的 currentUser
    },
    methods: {
      handleRegister() {
        // this.successMessage = '注册成功！';
        // this.errorMessage = '';
        console.log(this.password + " " + this.confirmPassword)
      //   const requestBody = {
      //     name: this.username,
      //     password: this.password,
      //     conform: this.confirmPassword // 确认密码字段
      // };
        // 在这里可以进行后续操作，如发送请求到后端进行注册
        // axios.post('http://localhost:8085/user/register', requestBody)
        axios.post('http://localhost:8085/user/register', null, {
            params: {
              name: this.username,
              password: this.password,
              conform: this.confirmPassword // 确认密码字段
            }
        })
        .then((response) => {
          console.log(response.data)
            if (response.data.code === 1) {
                this.successMessage = '注册成功';
                this.errorMessage = '';
                this.goToHome();
                this.$store.dispatch('login', response.data.data); // 登录成功后存储用户信息
            } else {
                this.errorMessage = response.data.msg || '注册失败';
                this.successMessage = '';
            }
        })
      },
      goToHome(){
        this.$router.push("/home")
      },
      backToLogin(){
        this.$router.push('/')
      }
    }
  };
  </script>
  
  <style scoped>

.button-group {
    display: flex;
    justify-content: space-between; /* 两个按钮之间的空间 */
  }
  .register-container {
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
  
  button {
    width: 100%;
    padding: 10px;
    background-color: #007bff;
    border: none;
    color: white;
    border-radius: 4px;
    cursor: pointer;
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
  
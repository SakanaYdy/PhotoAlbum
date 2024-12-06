<template>
    <div class="common-layout">
      <el-container>
        <el-header>
          <el-menu
            :default-active="activeIndex2"
            class="el-menu-demo"
            mode="horizontal"
            background-color="#545c64"
            text-color="#fff"
            active-text-color="#ffd04b"
            @select="handleSelect"
          >
            <el-menu-item index="1" @click="toHome">首页</el-menu-item>
            <el-sub-menu index="2" v-if="currentUser.role == 'admin'">
            <template #title>管理页面</template>
            <el-menu-item index="2-1" @click="goToManageLabel">类别管理</el-menu-item>
            <el-menu-item index="2-2" @click="goToManageAlbum">相册管理</el-menu-item>
            <el-menu-item index="2-3" @click="goToManageUser">用户管理</el-menu-item>
          </el-sub-menu>
          <!-- <el-menu-item index="2" v-if="currentUser.role == 'admin'" @click="goToManageAlbum">相册管理</el-menu-item> -->
            <el-menu-item index="3" @click="goToPerson">个人相册</el-menu-item>
            <el-menu-item index="4" @click="logout">登出</el-menu-item>
            <el-menu-item index="5" @click="goToNOtice">
              <el-icon><Bell />通知</el-icon>
            </el-menu-item>
            <el-menu-item index="6">
              <div v-if="currentUser">{{ currentUser.name }}</div>
            </el-menu-item>
          </el-menu>
        </el-header>
        <el-main>
            <el-table :data="users" style="width: 100%" stripe = true>
            <el-table-column fixed prop="id" label="用户ID" width="150" />
            <el-table-column prop="name" label="用户名" width="120" />
            <el-table-column prop="role" label="角色" width="120" />
            <el-table-column prop="phone" label="电话" width="180" >
            <template #default="scope">
              {{ scope.row.phone || '未提供' }}
            </template>
          </el-table-column>
          <el-table-column prop="email" label="邮箱" width="180">
            <template #default="scope">
              {{ scope.row.email || '未提供' }}
            </template>
          </el-table-column>
            <el-table-column prop="status" label="状态" width="100">
            <template #default="scope">
              <el-tag :type="scope.row.status === 0 ? 'success' : 'danger'">
                {{ scope.row.status === 0 ? '启用' : '禁用' }}
              </el-tag>
            </template>
          </el-table-column>
            <el-table-column fixed="right" label="Operations" min-width="120">
            <template #default="scope">
                <el-button link type="primary" size="small" @click="handleClick(scope.row.name)">
                Detail
                </el-button>
                <el-button link type="primary" size="small">Edit</el-button>
            </template>
            </el-table-column>
        </el-table>
      </el-main>
    </el-container>
    </div>
  </template>
  
  <script>
  import { mapState } from 'vuex'; // 引入 Vuex 的 mapState
  import axios from 'axios';
  export default {
    name: 'ManageLabel',
    computed: {
        ...mapState(['currentUser']), // 映射 Vuex 中的 currentUser
      },

    mounted() {
        this.fetchUsers(); // 组件挂载时获取用户信息
    },
    data() {
        return {
        users: [],
        activeIndex2: '2-3', // 默认选中的菜单项
        };
    },
    methods: {
        async fetchUsers() {
            try {
                const response = await axios.get('http://localhost:8085/admin/user'); // 向后端发送请求
                if (response.data.code === 1) {
                    this.users = response.data.data; 
                } else {
                this.$message.error(`获取用户列表失败: ${response.data.msg}`);
                }
            } catch (error) {
                console.error(error);
                this.$message.error('获取用户列表失败');
            }
        },
        handleClick(username) {
          axios.post('http://localhost:8085/admin/changeUser',null, { 
            params:{ username: username }  
          })
            .then(response => {
              console.log('修改成功:', response.data);
              // 刷新label
              this.fetchUsers();
              // 根据响应数据处理成功后的逻辑，比如显示提示信息
            })
            .catch(error => {
              console.error('修改失败:', error);
              // 处理错误，比如显示错误提示
            });
        },

        goToManageAlbum(){
            this.$router.push("/manage")
        },
        goToNOtice(){
            this.$router.push("/notice")
        },
        goToPerson(){
            this.$router.push("/person")
        },
        logout(){
            this.$store.dispatch('logout'); // 登录成功后存储用户信息
            this.$router.push("/")
        },
        toHome() {
            this.$router.push("/home");
        },
        goToManageLabel(){
            this.$router.push("/manageLabel")
        },
        goToManageUser(){
            this.$router.push("/manageUser")
        }
    }
    }
  </script>
  
  <style scoped>

  </style>
  
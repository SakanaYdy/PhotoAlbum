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
            <el-descriptions title="Width horizontal list" border>
      <el-descriptions-item
        :rowspan="2"
        :width="140"
        label="Photo"
        align="center"
      >
        <el-image
          style="width: 100px; height: 100px"
          src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"
        />
            </el-descriptions-item>
            <el-descriptions-item label="Username">kooriookami</el-descriptions-item>
            <el-descriptions-item label="Telephone">18100000000</el-descriptions-item>
            <el-descriptions-item label="Place">Suzhou</el-descriptions-item>
            <el-descriptions-item label="Remarks">
                <el-tag size="small">School</el-tag>
            </el-descriptions-item>
            <el-descriptions-item label="Address">
                No.1188, Wuzhong Avenue, Wuzhong District, Suzhou, Jiangsu Province
            </el-descriptions-item>
            </el-descriptions>
  
      </el-main>
    </el-container>
    </div>
  </template>
  
  <script>
  import { mapState } from 'vuex'; // 引入 Vuex 的 mapState
  export default {
    name: 'ManageLabel',
    computed: {
        ...mapState(['currentUser']), // 映射 Vuex 中的 currentUser
      },

    data() {
        return {
        users: [],
        activeIndex2: '2-3', // 默认选中的菜单项
        };
    },
    methods: {
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
  
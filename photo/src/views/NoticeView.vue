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
          <el-menu-item index="5" @click="goToNotifications">
            <el-icon><Bell />通知</el-icon>
          </el-menu-item>
          <el-menu-item index="6" @click="goToFav">收藏</el-menu-item>
          <el-menu-item index="7">
            <div v-if="currentUser">{{ currentUser.name }}</div>
          </el-menu-item>
        </el-menu>
      </el-header>
      <el-main>
      <h1>消息通知</h1>
      <el-card v-for="(notification, index) in notifications" :key="index" class="notification-card" @click="viewNotification(notification)">
        <div class="notification-content">
          <div class="notification-title">{{ notification.from }} {{ notifications.to }}</div>
          <span class="notification-time">{{ formatTime(notification.time) }}</span>
        </div>
        <p class="notification-message">{{ notification.notice }}</p>
      </el-card>
    </el-main>

    </el-container>
  </div>
</template>

<script>
import axios from 'axios';
import { mapState } from 'vuex'; // 引入 Vuex 的 mapState
export default {
  name: 'NoticeView',
  data() {
    return {
      notifications: [],
      activeIndex2: '5', // 当前活动的菜单项
    };
  },
  computed: {
      ...mapState(['currentUser']), // 映射 Vuex 中的 currentUser
    },
  mounted() {
    this.fetchNotices();
  },
  methods: {
    goToFav(){
      this.$router.push("/favorite")
    },
    async fetchNotices() {
      try {
        // const response = await axios.post('http://localhost:8085/notice/getNotice', { username: 'ydy' });
        const response = await axios.post('http://localhost:8085/notice/getNotice', null, {
          params: { username : this.currentUser.name } // 使用 params 传递查询参数
        });
        this.notifications = response.data.data; // 根据后端返回的数据结构进行调整
      } catch (error) {
        console.error('获取通知失败:', error);
      }
    },
    goToPerson() {
      this.$router.push("/person");
    },
    logout() {
      this.$store.dispatch('logout'); 
      this.$router.push("/");
    },
    toHome() {
      this.$router.push("/home");
    },
    goToNotifications() {
      // 处理通知点击，可能跳转到相应的页面
    },
    viewNotification(notification) {
      // 跳转到通知详情页或执行相应操作
      console.log('查看通知:', notification);
      // 例如：this.$router.push(`/notification/${notification.id}`);
    },
    formatTime(time) {
      return time.toLocaleString();
    }
  }
}
</script>

<style scoped>
.notification-card {
  margin: 5px 0;
  cursor: pointer;
  transition: background-color 0.3s;
  padding: 10px;
  font-size: 14px;
  display: flex;
  flex-direction: column; /* 垂直方向布局 */
}

.notification-content {
  display: flex; /* 使用 Flexbox 布局 */
  justify-content: space-between; /* 在主轴上分配空间 */
  align-items: center; /* 垂直居中对齐 */
}

.notification-title {
  font-weight: bold; /* 可选：加粗标题 */
}

.notification-time {
  font-size: 10px;
  color: #999;
}

.notification-message {
  margin-top: 5px; /* 在消息和内容之间添加间距 */
}
</style>


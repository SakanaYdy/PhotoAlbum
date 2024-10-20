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
          <el-sub-menu index="2">
            <template #title>相册管理</template>
            <el-menu-item index="2-1">相册类别</el-menu-item>
            <el-menu-item index="2-2">item two</el-menu-item>
            <el-menu-item index="2-3">item three</el-menu-item>
            <el-sub-menu index="2-4">
              <template #title>item four</template>
              <el-menu-item index="2-4-1">item one</el-menu-item>
              <el-menu-item index="2-4-2">item two</el-menu-item>
              <el-menu-item index="2-4-3">item three</el-menu-item>
            </el-sub-menu>
          </el-sub-menu>
          <el-menu-item index="3" @click="goToPerson">个人相册</el-menu-item>
          <el-menu-item index="4" @click="logout">登出</el-menu-item>
          <el-menu-item index="5" @click="goToNotifications">
            <el-icon><Bell />通知</el-icon>
          </el-menu-item>
          <el-menu-item index="6">
            <div v-if="currentUser">{{ currentUser.name }}</div>
          </el-menu-item>
        </el-menu>
      </el-header>
      <el-main>
      <h1>消息通知</h1>
      <el-card v-for="(notification, index) in notifications" :key="index" class="notification-card" @click="viewNotification(notification)">
        <div class="notification-content">
          <div class="notification-title">{{ notification.title }}</div>
          <span class="notification-time">{{ formatTime(notification.time) }}</span>
        </div>
        <p class="notification-message">{{ notification.message }}</p>
      </el-card>
    </el-main>

    </el-container>
  </div>
</template>

<script>
export default {
  name: 'NoticeView',
  data() {
    return {
      notifications: [
        { title: '系统更新', message: '您的系统已成功更新到最新版本。', time: new Date() },
        { title: '新消息', message: '您有一条新的消息来自管理员。', time: new Date() },
        { title: '活动通知', message: '即将开始的活动，请做好准备。', time: new Date() },
        // 更多通知...
      ],
    };
  },
  methods: {
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


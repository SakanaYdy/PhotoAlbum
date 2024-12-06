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
          <el-input v-model="newLabelName" placeholder="请输入标签名称" style="width: 200px; margin-right: 10px;"></el-input>
  <el-button type="primary" @click="createLabel(newLabelName)">新建 Label</el-button>

            <el-table :data="labels" style="width: 100%" stripe = true>
            <el-table-column prop="labelName" label="类别名称" width="120" />
            <el-table-column prop="isDelete" label="状态" width="100">
            <template #default="scope">
              <el-tag :type="scope.row.isDelete === 0 ? 'success' : 'danger'">
                {{ scope.row.isDelete === 0 ? '启用' : '禁用' }}
              </el-tag>
            </template>
          </el-table-column>
            <el-table-column fixed="right" label="Operations" min-width="120">
              <template #default="scope">
              <el-button link type="primary" size="small" @click="handleClick(scope.row)">
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
        this.fetchLabels(); // 组件挂载时获取用户信息
        
    },
    data() {
        return {
        newLabelName: '', // 新标签名称
        labels: [],
        activeIndex2: '2-1', // 默认选中的菜单项
        };
    },
    methods: {
      createLabel(newLabelName) {
      
      if (!newLabelName) {
        this.$message.error('标签名称不能为空');
        return;
      }

      // 发送请求创建新标签
      axios.post('http://localhost:8085/admin/addLabel', null, { params:{
          labelName: newLabelName
      } })
        .then(() => {
          this.$message.success('标签创建成功');
          this.fetchLabels();
          this.newLabelName = ''; // 清空输入框
        })
        .catch(error => {
          console.error('创建标签失败:', error);
          this.$message.error('创建标签失败');
        });
    },
        async fetchLabels() {
            try {
                const response = await axios.get('http://localhost:8085/admin/label'); // 向后端发送请求
                if (response.data.code === 1) {
                    this.labels = response.data.data; // 更新相册数据
                } else {
                this.$message.error(`获取用户列表失败: ${response.data.msg}`);
                }
            } catch (error) {
                console.error(error);
                this.$message.error('获取用户列表失败');
            }
        },
        handleClick(row) {
          const labelName = row.labelName;
          axios.post('http://localhost:8085/admin/changeLabel',null, { 
            params:{ labelName: labelName }  
          })
            .then(response => {
              console.log('修改成功:', response.data);
              // 刷新label
              this.fetchLabels();
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
  
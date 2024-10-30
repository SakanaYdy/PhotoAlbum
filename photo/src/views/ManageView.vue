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
  
        <!-- 多个相册显示 -->
        <el-main>
      <div class="block text-center">
        <span class="demonstration">相册展示</span>
        <el-row :gutter="20">
          <el-col v-for="(album, index) in albums" :key="index" :span="8">
            <div class="album" @click="openAlbum(album)" :class="album-container">
              <!-- <h3 class="album-title">{{ album.albumName }}  {{ album.owner }}</h3> -->
              <div class="album-header">
                  <h3 class="album-title">相册名: {{ album.albumName }}</h3>
                  <span class="album-owner">创建者：{{ album.owner }}</span>
              </div>
              <img v-if="album.avatar_url" :src="album.avatar_url" alt="Album Cover" class="album-cover"/>
            </div>
            <el-icon><Pointer /></el-icon>
            <el-icon><Star /></el-icon>
            <el-icon><StarFilled /></el-icon>
            <el-row :gutter="20">
              <el-col :span="10">
                
                <!-- :disabled="album.recommend"  -->
                <el-button 
                    type="success" 
                    @click="toggleRecommend(album)" 
                    :style="{ backgroundColor : album.recommend ? '#d9d9d9' : '' }">
                    推荐
                </el-button>
                <div class="grid-content ep-bg-purple">推荐数：{{ album.recommends }}</div>
              </el-col>
              <el-col :span="10">
                <div class="grid-content ep-bg-purple"></div>
                <!-- :disabled="album.recommend"  -->
                <el-button 
                    type="success" 
                    @click="openDeleteDialog(album)" >
                    删除
                </el-button>
              </el-col>
              <el-col :span="6">
                <div class="grid-content ep-bg-purple" />
              </el-col>
            </el-row>
          </el-col>
        </el-row>
      </div>
    </el-main>
      </el-container>

      <el-dialog title="确认删除相册" v-model="deleteDialogVisible">
        <span>请输入删除相册原因：</span>
        <el-input v-model="deleteReason" placeholder="删除原因"></el-input>
        <template v-slot:footer>
            <el-button @click="deleteDialogVisible = false">取 消</el-button>
            <el-button type="danger" @click="confirmDelete()">确 定</el-button>
        </template>
    </el-dialog>
  
     <!-- 弹窗 -->
     <el-dialog
        v-model="dialogVisible"
        :title="currentAlbum.name"
        width="600"
        center
      >
        <div class="album-images">
          <img
            v-for="(image, index) in currentAlbum.images"
            :key="index"
            :src="image"
            alt="相册图片"
            class="album-image"
          />
        </div>
        
        <div class="comment-section">  
        <!-- 显示之前的评论 -->
        <div class="previous-comments" v-if="comments.length > 0" style="margin-top: 20px;">
          <h4>之前的评论:</h4>
          <el-card v-for="(prevComment, index) in comments" :key="index" class="comment-card">
            <div>
              <strong>{{ prevComment.commenter }}:</strong> {{ prevComment.comment }}
              <el-icon  @click.stop="openDeleteComment(prevComment)"><Delete /></el-icon>
              <el-button 
                type="danger" 
                size="mini" 
                @click.stop="openDeleteComment(prevComment)"
                style="margin-left: 10px;">
                删除
              </el-button>
            </div>
          </el-card>
        </div>
      </div>

      <el-dialog title="确认删除评论" v-model="deleteCommentVisible">
        <span>请输入删除评论原因：</span>
        <el-input v-model="deleteReason" placeholder="删除原因"></el-input>
        <template v-slot:footer>
            <el-button @click="deleteCommentVisible = false">取 消</el-button>
            <el-button type="danger" @click="confirmDeleteComment()">确 定</el-button>
        </template>
    </el-dialog>

        
        <input
          type="file"
          @change="handleImageUpload"
          accept="image/*"
          style="display: none;"
          ref="fileInput"
        />
        
        <template #footer>
          <div class="dialog-footer">
            <el-button @click="dialogVisible = false">关闭</el-button>
          </div>
        </template>
      </el-dialog>

    </div>
  </template>
  
  <script>
  import { ref } from 'vue';
  import axios from 'axios';
  import { mapState } from 'vuex'; // 引入 Vuex 的 mapState
  
  export default {
    name: 'NavBar',
    computed: {
        ...mapState(['currentUser']), // 映射 Vuex 中的 currentUser
      },
    setup() {
      const dialogVisible = ref(false); // 对话框是否可见
      const currentAlbum = ref({ name: '', images: [] ,owner: ''}); // 当前相册，初始包含 name 属性
     
  
      const album1 = ref([]);
      const createAlbumVisible = ref(false); // 控制弹窗显示
      const newAlbum = ref({ name: '' }); // 新相册数据
  
  
      const showCreateAlbumDialog = () => {
        createAlbumVisible.value = true; // 显示弹窗
      };
  
      const coverImage = ref(null); // 定义封面图片变量
  
      return {
        album1,
        newAlbum,
        dialogVisible,
        currentAlbum,
        createAlbumVisible,
        showCreateAlbumDialog,
        coverImage
      };
    },
    data() {
      return {
        albums: [], // 初始化为空数组
        activeIndex2: '2-2', // 当前活动的菜单项
        comment: '', // 存储当前输入的评论
        comments: [],  // 存储之前的评论
        deleteReason: '', // 删除原因
        deleteDialogVisible: false, // 控制弹窗的显示
        deleteCommentVisible: false
      };
    },
    mounted() {
      this.fetchAlbumsWithLike(); // 组件挂载后获取相册数据
    },
    methods: {
      goToManageAlbum(){
      this.$router.push("/manage")
    },
      goToManageLabel(){
            this.$router.push("/manageLabel")
        },
        goToManageUser(){
            this.$router.push("/manageUser")
        },
      toggleRecommend(album) {
      if (!album.recommend) {
        // console.log("点赞后:" + !album.like)
         // 发送请求到后端进行点赞操作
        axios.post('http://localhost:8085/admin/recommend', null, {
          params: { albumName : album.albumName, username : this.currentUser.name, albumOwner: album.owner}
        })
        .then(() => {
            this.fetchAlbumsWithLike();
        })
      }else{
        axios.post('http://localhost:8085/admin/disRecommend', null, {
          params: { albumName : album.albumName, username : this.currentUser.name, albumOwner: album.owner}
        })
        .then(() => {
            this.fetchAlbumsWithLike();
        })
      }
    },
    async fetchAlbumsWithLike() {
      try {
        const response = await axios.post('http://localhost:8085/photo/getAll_user',null,{
          params: { username : this.currentUser.name }
        }); // 向后端发送请求
        if (response.data.code === 1) {
          this.albums = response.data.data; // 更新相册数据
          console.log(this.albums)
        } else {
          this.$message.error(`获取相册失败: ${response.data.msg}`);
        }
      } catch (error) {
        console.error(error);
        this.$message.error('获取相册数据时出错');
      }
    },
      // 删除评论
      deleteComment(comment,reason) {
        console.log("删除评论内容:" + comment.comment + "原因:" + reason + "相册名:" + this.currentAlbum.name) 
        this.$confirm('确定要删除这条评论吗?', '警告', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          const deleteCommentDto = {
            album_name: this.currentAlbum.name, // 当前评论信息
            commenter: comment.commenter,
            comment: comment.comment,
            maker: this.currentUser.name,
            reason: reason,
        };
          // 调用 API 删除评论
          axios.post('http://localhost:8085/admin/deleteComment', deleteCommentDto)
          .then(() => {
            this.$message.success('删除成功');
            this.fetchPhotosWithComments(this.currentAlbum.name)
            // 从本地数组中删除评论
            // this.comments.splice(index, 1);
          })
        }).catch(() => {
          this.$message.info('已取消删除');
        });
      },
      openDeleteComment(comment) {
          console.log("删除评论 所有者" + comment.commenter)
          this.commentToDelete = comment; // 保存待删除相册
          
          this.deleteCommentVisible = true; // 显示弹窗
          this.deleteReason = ''; // 清空输入框
      },
      confirmDeleteComment() {
        if (this.deleteReason.trim() === '') {
            this.$message.error('请填写删除原因');
            return;
        }
        
        // 这里可以添加你的删除逻辑，比如调用 API
        // 假设你有一个删除相册的方法 deleteAlbum(album)
        // console.log("评论所有者：" + this.commentToDelete.commenter);
        this.deleteComment(this.commentToDelete, this.deleteReason);
        
        this.deleteCommentVisible = false; // 关闭弹窗
        this.commentToDelete = null; // 清空待删除相册
    },

      openDeleteDialog(album) {
        console.log("删除相册 所有者" + album.owner)
        this.albumToDelete = album; // 保存待删除相册
        
        this.deleteDialogVisible = true; // 显示弹窗
        this.deleteReason = ''; // 清空输入框
    },
      confirmDelete() {
        if (this.deleteReason.trim() === '') {
            this.$message.error('请填写删除原因');
            return;
        }
        
        // 这里可以添加你的删除逻辑，比如调用 API
        // 假设你有一个删除相册的方法 deleteAlbum(album)
        console.log("相册所有者：" + this.albumToDelete.owner);
        this.deleteAlbum(this.albumToDelete, this.deleteReason,this.albumToDelete.owner);
        
        this.deleteDialogVisible = false; // 关闭弹窗
        this.albumToDelete = null; // 清空待删除相册
    },
    deleteAlbum(album, reason,owner) {
        // 实现你的删除逻辑
        console.log(`删除相册: ${album.albumName}, 原因: ${reason}`,`相册所有者: ${owner}`);
        const deleteAllbumDto = {
          albumName: album.albumName, // 当前相册的名称
          owner: owner, 
          reason: reason,
          maker: this.currentUser.name
          
        };
        axios.post('http://localhost:8085/admin/deleteAlbum', deleteAllbumDto)
            .then(() => {
                // 处理成功响应
                this.$message.success('删除成功');
                this.fetchAlbums();
                // 更新相册列表，移除已删除的相册
                // this.albums = this.albums.filter(album => album.albumName !== albumName);

            })
            // .catch(error => {
            //     // 处理错误响应
            //     this.$message.error('删除失败：' + error.response.data.message);
            // });
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
      async fetchAlbums() {
        try {
          const response = await axios.get('http://localhost:8085/photo/getAll'); // 向后端发送请求
          if (response.data.code === 1) {
            this.albums = response.data.data; // 更新相册数据
          } else {
            this.$message.error(`获取相册失败: ${response.data.msg}`);
          }
        } catch (error) {
          console.error(error);
          this.$message.error('获取相册数据时出错');
        }
      },
      toHome() {
        this.$router.push("/home");
      },
      openAlbum(album) {
        console.log(album); // 确认相册对象
        // this.currentAlbum.images = album.images; // 设置当前相册的图片
        this.currentAlbum.name = album.albumName
        this.currentAlbum.owner = album.owner
        console.log(album.albumName)
        this.fetchPhotosWithComments(album.albumName); // 获取相册的图片
        this.dialogVisible = true; // 打开对话框
      },
      async fetchPhotosWithComments(albumName) {
        try {
          console.log(albumName);
          const response = await axios.post('http://localhost:8085/photo/getPhotos_comment', null, {
            params: { albumName }
          });
  
          if (response.data.code === 1) {
            const { urls, comments } = response.data.data || {}; // 使用空对象避免解构错误
            this.currentAlbum.images = urls || []; // 如果 urls 为 null，设置为空数组
            this.comments = comments || []; // 如果 comments 为 null，设置为空数组
          } else {
            this.$message.error(`获取相册图片失败: ${response.data.msg}`);
          }
        } catch (error) {
          console.error(error);
          this.$message.error('获取相册图片时出错');
        }
      },
    }
  }
  </script>
  
  <style scoped>

.album-header {
    display: flex;
    justify-content: space-between; /* 左右两侧对齐 */
    align-items: center; /* 垂直居中对齐 */
}

.dialog-footer {
    display: flex;
    justify-content: flex-end;
}
  
  .album-container {
    border: 1px solid #e4e7ed; /* 添加边框 */
    border-radius: 8px; /* 圆角 */
    padding: 10px; /* 内边距 */
    transition: transform 0.3s; /* 添加过渡效果 */
    cursor: pointer; /* 鼠标指针样式 */
  }
  
  .album-container:hover {
    transform: scale(1.05); /* 鼠标悬停时放大效果 */
  }
  
  .album-title {
    font-size: 18px; /* 相册名称字体大小 */
    margin-bottom: 10px; /* 相册名称与图片的间距 */
    margin: 0%;
  }
  
  
  .album-image {
    height: 200px; /* 设置图片高度 */
    object-fit: cover; /* 保持图片比例 */
    width: 100%; /* 根据需要设置宽度 */
    max-width: calc(100% - 10px); /* 确保不会超出相册容器 */
  }
  
  .album {
    border: 1px solid #e4e7ed; /* 添加边框 */
    border-radius: 8px; /* 圆角 */
    padding: 10px; /* 内边距 */
    text-align: center; /* 文本居中 */
  }
  
  .album-cover {
    width: 100%; /* 设置宽度为100% */
    height: 200px; /* 设置固定高度 */
    object-fit: cover; /* 保持比例并裁剪 */
    border-radius: 4px; /* 圆角效果 */
  }

  
  .album-images {
    display: flex;
    flex-wrap: wrap;
    justify-content: center;
    margin-top: 10px;
  }
  
  .album-image {
    width: 100px; /* 图片宽度 */
    height: 100px; /* 图片高度 */
    object-fit: cover; /* 保持宽高比，填充整个区域 */
    margin: 5px; /* 图片间隔 */
    border-radius: 5px; /* 圆角 */
  }
  
  .dialog-footer {
    display: flex;
    justify-content: flex-end;
  }
  </style>
  
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
          <el-menu-item index="6" @click="goToFav">收藏</el-menu-item>
          <el-menu-item index="7" @click="goToInfo">
            <div v-if="currentUser">{{ currentUser.name }}</div>
            <!-- <div v-if="currentUser">{{ currentUser.role }}</div> -->
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
              <!-- <h3 class="album-title" >相册名: {{ album.albumName }}  创建者： {{ album.owner }}</h3> -->
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
                <div class="grid-content ep-bg-purple">点赞数: {{ album.thumbs }}</div>
                <!-- :disabled="album.like"  -->
                <el-button 
                    type="primary" 
                    @click="toggleLike(album)" 
                    :style="{ backgroundColor: album.like ? '#d9d9d9' : '' }">
                    点赞
                </el-button>
              </el-col>
              <el-col :span="10">
                <div class="grid-content ep-bg-purple">收藏数：{{ album.favorites }}</div>
                <!-- :disabled="album.favorite"  -->
                <el-button 
                    type="warning" 
                    @click="toggleFavorite(album)" 
                    :style="{ backgroundColor: album.favorite ? '#d9d9d9' : '' }">
                    收藏
                </el-button>
              </el-col>
              <!-- <el-col :span="6">
                <div class="grid-content ep-bg-purple">推荐数：{{ album.recommends }}</div>
                :disabled="album.recommend" 
                <el-button 
                    type="success" 
                    @click="toggleRecommend(album)" 
                    :style="{ backgroundColor : album.recommend ? '#d9d9d9' : '' }">
                    推荐
                </el-button>
              </el-col> -->
              <!-- <el-col :span="6">
                <div class="grid-content ep-bg-purple" />
              </el-col> -->
            </el-row>
          </el-col>
        </el-row>

      </div>
    </el-main>
    </el-container>
    

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
      
      <!-- 添加评论区域 -->
      <div class="comment-section">
        <el-input
          type="textarea"
          v-model="comment"
          placeholder="在此输入评论"
          rows="4"
          style="margin-top: 10px;"
        />
        <el-button type="primary" @click="submitComment">提交评论</el-button>
        
        <!-- 显示之前的评论 -->
        <div class="previous-comments" v-if="comments.length > 0" style="margin-top: 20px;">
          <h4>之前的评论:</h4>
          <el-card v-for="(prevComment, index) in comments" :key="index" class="comment-card">
            <div><strong>{{ prevComment.commenter }}:</strong> {{ prevComment.comment }}</div>
          </el-card>
        </div>
      </div>
      
      <input
        type="file"
        @change="handleImageUpload"
        accept="image/*"
        style="display: none;"
        ref="fileInput"
      />
      
      <template #footer>
        <div class="dialog-footer">
          <!-- <el-button @click="addImage" type="primary">添加图片</el-button> -->
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
    const currentAlbum = ref({ name: '', images: [] ,owner: '', thumbs: 0,favorites: 0,recommends: 0,like : false,favorite : false,recommend : false}); // 当前相册，初始包含 name 属性
   

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
      activeIndex2: '1', // 当前活动的菜单项
      comment: '', // 存储当前输入的评论
      comments: [],  // 存储之前的评论
    };
  },
  mounted() {
    // this.fetchAlbums(); // 组件挂载后获取相册数据
    this.fetchAlbumsWithLike();
    console.log(this.albums)
  },
  methods: {
    goToManageLabel(){
        this.$router.push("/manageLabel")
    },
    goToManageUser(){
        this.$router.push("/manageUser")
    },
    goToInfo(){
        this.$router.push("/personalInfo")
    },  
    goToFav(){
      this.$router.push("/favorite")
    },
    async submitComment() {
      console.log("发表评论")
      if (this.comment) {
        const albumCommentDto = {
          album_name: this.currentAlbum.name, // 当前相册的名称
          commenter: this.currentUser.name, // 当前用户的名称
          comment: this.comment // 用户输入的评论
        };

        try {
          const response = await axios.post('http://localhost:8085/comment/add', albumCommentDto);

          if (response.data.code === 1) {
            // 添加成功后，将新评论添加到评论数组中
            this.comments.push({
              commenter: this.currentUser.name,
              comment: this.comment
            });
            this.comment = ''; // 清空评论输入框
          } else {
            this.$message.error(`添加评论失败: ${response.data.msg}`);
          }
        } catch (error) {
          console.error(error);
          this.$message.error('提交评论时出错');
        }
      } else {
        this.$message.warning('请输入评论内容');
      }
    },
    toggleLike(album) {
      console.log("点赞相册" + album.albumName)
      console.log("点赞前:" + album.like)
      if (!album.like) {
        console.log("点赞后:" + !album.like)
         // 发送请求到后端进行点赞操作
        axios.post('http://localhost:8085/photo/likeAlbum', null, {
          params: { albumName : album.albumName, username : this.currentUser.name, albumOwner: album.owner}
        })
        .then(() => {
            this.fetchAlbumsWithLike();
        })
      }else{
        axios.post('http://localhost:8085/photo/dislikeAlbum', null, {
          params: { albumName : album.albumName, username : this.currentUser.name, albumOwner: album.owner}
        })
        .then(() => {
            this.fetchAlbumsWithLike();
        })
      }
    },
    toggleFavorite(album) {
      if (!album.favorite) {
        // console.log("点赞后:" + !album.favorite)
         // 发送请求到后端进行点赞操作
        axios.post('http://localhost:8085/photo/favAlbum', null, {
          params: { albumName : album.albumName, username : this.currentUser.name, albumOwner: album.owner}
        })
        .then(() => {
            this.fetchAlbumsWithLike();
        })
      }else{
        axios.post('http://localhost:8085/photo/disfavAlbum', null, {
          params: { albumName : album.albumName, username : this.currentUser.name, albumOwner: album.owner}
        })
        .then(() => {
            this.fetchAlbumsWithLike();
        })
      }
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
      this.$store.dispatch('logout'); // 登出之后清除用户信息
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
    async fetchPhotos(albumName) {
      try {
        console.log(albumName)
        const response = await axios.post('http://localhost:8085/photo/getPhotos', null, {
          params: { albumName } // 使用 params 传递查询参数
        });

        if (response.data.code === 1) {
          this.currentAlbum.images = response.data.data; // 更新当前相册的图片
        } else {
          this.$message.error(`获取相册图片失败: ${response.data.msg}`);
        }
      } catch (error) {
        console.error(error);
        this.$message.error('获取相册图片时出错');
      }
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
    handleImageUpload(event) {
      const files = event.target.files;
      if (files.length > 0) {
        const formData = new FormData();
        formData.append('albumName', this.currentAlbum.name); // 添加相册名称
        for (let i = 0; i < files.length; i++) {
          formData.append('files', files[i]); // 添加选中的文件
        }
        this.uploadPhotos(formData);
      }
    },
    async uploadPhotos(formData) {
      try {
        const response = await axios.post('http://localhost:8085/file/oss/upload/albumPhoto', formData, {
          headers: {
            'Content-Type': 'multipart/form-data'
          } 
        });

        // 检查响应数据
        if (response.data.code === 1) { // 成功
          console.log('上传成功:', response.data.data);
          // 将返回的 URL 添加到当前相册的图片列表中
          this.currentAlbum.images.push(...response.data.data);
          
          this.coverImage = ''; // 清空封面图片
          this.coverImageFile = null; // 清空文件引用
          this.newAlbum = null;

        } else {
          console.error('上传失败:', response.data.msg);
        }
      } catch (error) {
        console.error('上传过程中发生错误:', error);
      }
    },
      async handleUploadSuccess(response, file) {
      // 假设返回的数据格式是 { code: 1, msg: 'success', data: '上传后的图片链接' }
      if (response.code === 1) {
        this.coverImage = response.data; // 设置封面图片链接
        console.log(response.data)
        this.coverImageFile = file.raw; // 保存文件以便后续使用
        this.$message.success('封面图片上传成功');
      } else {
        this.$message.error(`上传失败: ${response.msg}`);
      }
    },
    
    beforeUpload(file) {
      const isImage = file.type.startsWith('image/');
      if (!isImage) {
        this.$message.error('只能上传图片文件');
      }
      return isImage; // 返回 false 以阻止上传
    },

    async createAlbum() {
      try {
        const formData = new FormData();
        formData.append('avatar', this.coverImageFile); // 假设你将上传的文件存储在 coverImageFile 中
        formData.append('albumName', this.newAlbum.name);
        // formData.append('owner', currentUser.name); // 根据需要修改
        formData.append('owner', this.currentUser.name);

        const response = await axios.post('http://localhost:8085/photo/create', formData, {
          headers: {
            'Content-Type': 'multipart/form-data'
          }
        });

        if (response.data.code === 1) {
          this.$message.success('相册创建成功');
          // 你可以在这里更新相册列表或其他逻辑
          this.createAlbumVisible = false; // 关闭弹窗
        } else {
          this.$message.error(`创建失败: ${response.data.msg}`);
        }
      } catch (error) {
        console.error(error);
        this.$message.error('创建相册时出错');
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
  margin: 0;
}

.album-data {
  font-size: 18px; /* 相册名称字体大小 */
  margin-bottom: 10px; /* 相册名称与图片的间距 */
}

/* .album-cover {
  width: 100%; 
  height: auto; 
  border-radius: 4px; 
} */

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
/* 
.album {
  border: 1px solid #ddd;
  border-radius: 5px;
  padding: 10px;
  background-color: #f9f9f9;
  cursor: pointer; /* 添加指针效果 */
/* } */

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

.avatar-uploader .avatar {
  width: 178px;
  height: 178px;
  display: block;
}

.avatar-uploader .el-upload {
  border: 1px dashed var(--el-border-color);
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: var(--el-transition-duration-fast);
}

.avatar-uploader .el-upload:hover {
  border-color: var(--el-color-primary);
}

.el-icon.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  text-align: center;
}
</style>

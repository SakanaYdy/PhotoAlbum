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
            <el-menu-item index="1">首页</el-menu-item>
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
            <el-menu-item index="3">个人相册</el-menu-item>
            <el-menu-item index="4">登出</el-menu-item>
            <el-menu-item index="5">
              <el-icon><Bell />通知</el-icon>
            </el-menu-item>
            
        </el-menu>
        
        </el-header>

        
        <!-- 多个相册显示 -->
        <el-main>
          <div class="block text-center">
            <span class="demonstration">相册展示</span>
            <el-row :gutter="20">
                <el-col
                    v-for="(album, index) in albums"
                    :key="index"
                    :span="8" 
                    >
                    <div class="album">
                        <h3>{{ album.name }}</h3> <!-- 显示相册名称 -->
                        <el-carousel height="200px" motion-blur>
                        <el-carousel-item v-for="(image, imgIndex) in album.images" :key="imgIndex">
                            <div class="carousel-item">
                            <img :src="image" alt="Carousel Image" />
                            </div>
                        </el-carousel-item>
                        </el-carousel>
                    </div>
                    </el-col>

            </el-row>
          </div>
        </el-main>
      </el-container>
    </div>
  </template>
  
  <script>
  // @ is an alias to /src
  export default {
  name: 'NavBar',
  data() {
    return {
      albums: [
        {
          name: '风景相册',
          images: [
            require('@/assets/1.png'),
            require('@/assets/2.png'),
            require('@/assets/3.jpeg'),
            require('@/assets/4.png')
          ]
        },
        {
          name: '人物相册',
          images: [
            require('@/assets/1.png'),
            require('@/assets/2.png'),
            require('@/assets/3.jpeg'),
            require('@/assets/4.png')
          ]
        },
        {
          name: '城市相册',
          images: [
            require('@/assets/1.png'),
            require('@/assets/2.png'),
            require('@/assets/3.jpeg'),
            require('@/assets/4.png')
          ]
        }
      ]
    };
  }
}

  </script>
  
  <style scoped>
  .album {
    border: 1px solid #ddd;
    border-radius: 5px;
    padding: 10px;
    background-color: #f9f9f9;
  }
  
  .carousel-item {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100%; /* 保持与走马灯高度一致 */
  }
  
  .carousel-item img {
    max-width: 100%;
    max-height: 100%;
    object-fit: cover; /* 保持宽高比，填充整个区域 */
  }
  </style>
  
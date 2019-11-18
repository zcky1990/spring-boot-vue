<template>
  <section class="section article">
    <div v-if="isCategoryExists" class="categories-container">
      <div class="categories">{{content.categoryArticle.name}}</div>
    </div>
    <div id="grid" class="content-container" tag="section">
      <div class="article-title">
        <div class="headline">{{content.article_title}}</div>
      </div>
      <div class="article-authors">
        <div class="authors-container" @click="seeAuthorsDetails(content.author.id)">
          <div class="image-authors" v-if="isHasAuthor">
            <v-img
              :src="content.author.image_profile_url"
              lazy-src="https://picsum.photos/10/6?image=11"
              aspect-ratio="1"
              class="grey lighten-2 rounded"
            >
              <template v-slot:placeholder>
                <v-layout fill-height align-center justify-center ma-0>
                  <v-progress-circular indeterminate color="grey lighten-5"></v-progress-circular>
                </v-layout>
              </template>
            </v-img>
          </div>
          <div class="author-desc" v-if="isHasAuthor">
            <div class="author">
              <div class="authors-name">{{content.author.firstname}} {{content.author.lastname}}</div>
              <div class="article-create-date">{{content.created_date}}</div>
            </div>
          </div>
        </div>
        <div v-if="isUserLoggin" class="follow-btn">
          <div
            v-if="!isFollowed"
            class="btn-follow"
            @click="followUnfollowAuthors(content.author.id)"
          >Follow</div>
          <div
            v-else
            class="btn-follow unfollow-btn"
            @click="followUnfollowAuthors(content.author.id)"
          >Unfollow</div>
        </div>
      </div>

      <div class="content">
        <div class="article-content-container">
          <div v-if="isUserLoggin" class="bookmark-container">
            <div v-if="!isUserBookmarkArticle" class="bookmark-btn" @click="bookmarkArticle">
              <v-tooltip bottom>
                <template v-slot:activator="{ on }">
                  <v-icon color="rgb(0, 209, 178)" v-on="on">bookmark</v-icon>
                </template>
                <span>Bookmark this Article</span>
              </v-tooltip>
            </div>

            <div v-else class="bookmark-btn" @click="removeBookmarkArticle">
              <v-tooltip bottom>
                <template v-slot:activator="{ on }">
                  <v-icon color="red" v-on="on">bookmark</v-icon>
                </template>
                <span>Unbookmark this Article</span>
              </v-tooltip>
            </div>
          </div>

          <div class="article-content">
            <div v-html="content.article_content"></div>
          </div>
        </div>
        <div v-if="isHaveReference" class="article-reference">
          <div class="reference-list">Reference</div>
          <div
            class="reference-items"
            v-for="items in content.reference_list"
            :key="items"
          >{{items}}</div>
        </div>
      </div>
    </div>
  </section>
</template>
<script>
import { EventBus } from "./../../EventBus.js";
import hljs from "highlight.js/lib/highlight";
import "highlight.js/styles/github.css";

export default {
  name: "article-components",
  props: {
    content: Object
  },
  data() {
    return {
      data: {
        id: "",
        userId: "",
        articleId: ""
      },
      bookmark: {
        createBookmark: "bookmarks/create",
        deleteBookmark: "bookmarks/delete/"
      },
      follow: {
        followUrl: "follow/authors/",
        unfollowUrl: "unfollow/authors/"
      },
      isFollowed: false,
      isBookmarked: false,
      isUserLoggin: false,
      isHaveReference: false
    };
  },
  created() {
    this.isUserLoggin = this.isLoggin(this.$session);
    if (this.isUserLoggin) {
      this.data.userId = this.getUserId(this.$session);
    }
  },
  methods: {
    followUnfollowAuthors: function(id) {
      if (this.isUserLoggin) {
        if (!this.isFollowed) {
          this.followAuthors(id);
        } else {
          this.unFollowAuthors(id);
        }
      } else {
        this.$router.push("/login");
      }
    },
    followAuthors: function(id) {
      let self = this;
      let headers = this.getHeaders(this.$session);
      this.post(
        this.follow.followUrl + id,
        {},
        headers,
        function(response) {
          if (response.status == 200) {
            if (response.data.error_message) {
              self.setMessage(response.data.error_message, 1);
            } else {
              let responseData = response.data.response;
              self.isFollowed = true;
            }
          }
        },
        function(e) {
          self.setMessage(e, 1);
        }
      );
    },
    unFollowAuthors: function(id) {
      let self = this;
      let headers = this.getHeaders(this.$session);
      this.delete(
        this.follow.unfollowUrl + id,
        headers,
        function(response) {
          if (response.status == 200) {
            self.isFollowed = false;
          }
        },
        function(e) {
          self.setMessage(e, 1);
        }
      );
    },
    seeAuthorsDetails: function(id) {
      let url = "/detail/" + id;
      this.$router.push(url);
    },
    setCssSideImage: function() {
      let elm = document.querySelectorAll(".image");
      if (elm.length > 0) {
        for (let i = 0; i < elm.length; i++) {
          let el = elm[i];
          if (el.className.includes("image-style-side")) {
            el.style.float = "right";
            el.style.maxWidth = "50%";
          }
          el.style.padding = "15px";
          el.style.textAlign = "center";
          el.styleposition = "relative";
          el.styleoverflow = "hidden";
          let child = el.children[0];
          child.style.maxWidth = "100%";
          child.style.display = "block";
          child.style.margin = "0 auto";
        }
      }
    },
    setCssQuote: function() {
      let elm = document.querySelectorAll("blockquote");
      if (elm.length > 0) {
        for (let i = 0; i < elm.length; i++) {
          let el = elm[i];
          el.style.background = "aliceblue";
          el.style.padding = "5px";
          el.style.borderLeft = "5px solid #00d1b2";
          el.style.marginBottom = "16px";
        }
      }
    },
    setBulletNumberingCss: function() {
      let bulletElList = document.querySelectorAll(
        "#grid > div.content > div.article-content-container > div.article-content > div > ul"
      );
      let numberingElList = document.querySelectorAll(
        "#grid > div.content > div.article-content-container > div.article-content > div > ol"
      );
      if (bulletElList.length > 0) {
        for (let i = 0; i < bulletElList.length; i++) {
          let el = bulletElList[i];
          el.style.marginBottom = "16px";
        }
      }
      if (numberingElList.length > 0) {
        for (let i = 0; i < numberingElList.length; i++) {
          let el = numberingElList[i];
          el.style.marginBottom = "16px";
        }
      }
    },
    bookmarkArticle: function() {
      this.addBookmark(this.data);
    },
    removeBookmarkArticle: function() {
      this.deleteBookmark(this.data.articleId);
    },
    addBookmark: function(data) {
      let self = this;
      delete self.data["id"];
      let headers = this.getHeaders(this.$session);
      this.post(
        this.bookmark.createBookmark,
        data,
        headers,
        function(response) {
          if (response.status == 200) {
            if (response.data.error_message) {
              self.setMessage(response.data.error_message, 1);
            } else {
              self.isBookmarked = true;
            }
          }
        },
        function(e) {
          self.setMessage(e, 1);
        }
      );
    },
    deleteBookmark: function(id) {
      let self = this;
      let headers = this.getHeaders(this.$session);
      this.delete(
        this.bookmark.deleteBookmark + id,
        headers,
        function(response) {
          if (response.status == 200) {
            if (response.data.error_message) {
              self.setMessage(response.data.error_message, 1);
            } else {
              self.isBookmarked = false;
            }
          }
        },
        function(e) {
          self.setMessage(e, 1);
        }
      );
    },
    setMessage: function(message, type) {
      let data = {};
      data.message = message;
      data.type = type;
      EventBus.$emit("SNACKBAR_TRIGGERED", data);
    }
  },
  updated() {
    this.data.articleId = this.content.id;
    this.isFollowed = this.content.isFollowed;
    this.isBookmarked = this.content.isBookmarked;
    if (this.content.reference_list) {
      this.isHaveReference = true;
    }
    this.setCssSideImage();
    this.setCssQuote();
    this.setBulletNumberingCss();
    document.querySelectorAll("pre p").forEach(block => {
      hljs.highlightBlock(block);
    });

    document.querySelectorAll("pre ol").forEach(block => {
      hljs.highlightBlock(block);
    });
  },
  computed: {
    isUserBookmarkArticle: function() {
      return this.isBookmarked;
    },
    isCategoryExists: function() {
      if (this.content.categoryArticle != undefined) {
        if (this.content.categoryArticle.name != null) {
          return true;
        } else {
          return false;
        }
      } else {
        return false;
      }
    },
    isHasAuthor: function() {
      if (this.content.author != undefined) {
        if (this.content.author != null) {
          return true;
        } else {
          return false;
        }
      } else {
        return false;
      }
    }
  }
};
</script>
<style scoped>
.article-authors {
  display: flex;
  flex-wrap: nowrap;
  flex-direction: row;
}
.authors-container {
  display: flex;
}
.author {
  padding-left: 20px;
  padding-top: 7px;
}
.rounded {
  border-radius: 66px;
  width: 64px;
  height: 64px;
}
.authors-name {
  font-size: 1.5em;
  font-weight: 600;
  line-height: 1.5;
}
.article-create-date {
  color: grey;
  margin-bottom: 5px;
}
.headline {
  font-size: 2.3rem !important;
  font-weight: 600;
  line-height: 1.5 !important;
  text-transform: capitalize;
}
.content {
  font-size: 1em;
}
.article-authors {
  padding-bottom: 16px;
}
.article-content {
  display: block;
}
.image > img {
  display: block;
  margin: 0 auto;
  max-width: 100%;
}
.categories {
  padding: 5px 15px 5px 15px;
  border: 1px solid rgb(0, 209, 178);
  border-radius: 15px;
  width: max-content;
  font-weight: 400;
}
.categories:hover {
  background: rgb(0, 209, 178);
  color: white;
}
.content-container {
  padding-top: 10px;
  padding-bottom: 16px;
}
.article-content-container {
  display: flex;
  flex-wrap: nowrap;
  flex-direction: row;
}
.follow-btn {
  color: #00d1b2;
  text-align: center;
  font-size: 12px;
  font-weight: 100;
  margin-left: 10px;
}
.unfollow-btn {
  color: chartreuse !important;
  border: 1px solid chartreuse !important;
}
.btn-follow {
  border: 1px solid #00d1b2;
  border-radius: 5px;
  padding-left: 5px;
  padding-right: 5px;
  margin-top: 5px;
  cursor: pointer;
}
@media only screen and (max-width: 600px) {
  .article-content-container {
    display: flex;
    flex-wrap: nowrap;
    flex-direction: column;
  }
}
.bookmark-container {
  width: 100px;
  padding-right: 20px;
}
.reference-list {
  font-size: 1.5rem;
  font-weight: 600;
  line-height: 2;
}
blockquote {
  background: aliceblue;
  padding: 5px;
  border-left: 5px solid #00d1b2;
}
</style>

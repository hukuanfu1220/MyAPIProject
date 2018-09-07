package io.renren.modules.app.entity;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Date;

@TableName("tb_blog")
public class BlogEntity implements Serializable {

    @TableId
    private Integer articleId;

    private String articleTitle;

    private String articleAuthor;

    private String articleContent;

    private Integer userId;

    private Date submissionDate;

    /**
     * @return article_id
     */
    public Integer getArticleId() {
        return articleId;
    }

    /**
     * @param articleId
     */
    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    /**
     * @return article_title
     */
    public String getArticleTitle() {
        return articleTitle;
    }

    /**
     * @param articleTitle
     */
    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    /**
     * @return article_author
     */
    public String getArticleAuthor() {
        return articleAuthor;
    }

    /**
     * @param articleAuthor
     */
    public void setArticleAuthor(String articleAuthor) {
        this.articleAuthor = articleAuthor;
    }

    /**
     * @return article_content
     */
    public String getArticleContent() {
        return articleContent;
    }

    /**
     * @param articleContent
     */
    public void setArticleContent(String articleContent) {
        this.articleContent = articleContent;
    }

    /**
     * @return user_id
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * @param userId
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * @return submission_date
     */
    public Date getSubmissionDate() {
        return submissionDate;
    }

    /**
     * @param submissionDate
     */
    public void setSubmissionDate(Date submissionDate) {
        this.submissionDate = submissionDate;
    }
}

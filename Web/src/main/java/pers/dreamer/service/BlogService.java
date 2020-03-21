package pers.dreamer.service;

import pers.dreamer.bean.Blog;
import pers.dreamer.bean.Blogcollection;
import pers.dreamer.bean.Blogcomments;
import pers.dreamer.bean.Blogvote;

import java.util.List;

public interface BlogService {

    List<Blog> findBlogsByPgeAndNameAndSatr(Integer page, Integer limitSize, String name, Integer star,Integer uid);

    List<Blogcollection> findBlogCollectionsByUid(Integer uid);

    Long findBlogsTotalByNameAndSatr(String name, Integer star, Integer uid);

    Integer findMyVoteByBidAndUid(Integer bid, Integer uid);

    Blog findBlogByBid(Integer bid);

    List<Blogcomments> findBlogCommentsByBid(Integer bid);

    Integer findMyVoteByCommentIdAndUid(Integer id, Integer uid);

    boolean insertBlogVote(Blogvote blogvote);

    Integer insertBlogReply(Integer bid,Integer cid,Integer uid,String text);

    Blogcomments findBlogCommentsById(Integer id);

    void updateBlogComments(Blogcomments blogcomments);

    void updateBlog(Blog blog);

    List<Blog> findBlogsByProblem(String s);

    void insertBlog(Blog blog);

    void deleteBlogByBid(Integer bid);

    void deleteCommentById(Integer cid);

    List<Blog> findBlogByState(int i);

    Blogcollection findBlogCollectionsByBidAndUid(Integer bid, Integer uid);

    Boolean findBlogCollectionsStatusByBidAndUid(Integer bid, Integer uid);

    Boolean isExistBlogByBid(Integer bid);

    void insertBlogcollection(Blogcollection blogcollection);

    void deleteBlogcollection(Blogcollection blogcollection);
}

package pers.dreamer.service.impls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.dreamer.dao.CommentMapper;
import pers.dreamer.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentMapper commentMapper;

}

package pers.dreamer.oj.judge.service;

import pers.dreamer.oj.judge.dto.CodeDto;
import pers.dreamer.oj.judge.pojo.Code;
import pers.dreamer.oj.judge.pojo.Problem;

public interface ProblemService {

    public Code findCodeBySid(Integer sid);

    public void updateSubmitListAndProbleminfoByCodeDto(CodeDto codeDto);

    public Problem findProblemByPid(Integer pid);

    public void insertProblem(Problem problem);

    public Problem findProblemByTitle(String title);

    public boolean delProblemByPid(Integer pid);
}

import lorun
import os
import sys
import json

runcfg = {
    'args': [],
    'fd_in': 0,
    'fd_out': 0,
    'fd_err': 0,
    'timelimit': 0,
    'memorylimit': 0,
    
}

def judge(cmd,file_in,file_out,time_limit,memory_limit,kind,spj):
    fin = open(file_in)
    fout = open(file_out,'w+')
    ferr = open('/bin/null')
    runcfg['args'] = cmd
    runcfg['fd_err'] = ferr.fileno()
    runcfg['fd_in'] = fin.fileno()
    runcfg['fd_out'] = fout.fileno()
    runcfg['timelimit'] = time_limit
    runcfg['memorylimit'] = memory_limit
    result = lorun.run(runcfg)
    fin.close()
    fout.close()
    if spj == 1:
        result = SPJudge(file_in,result)
    elif result['result'] == 0:
        file_ans = file_in.replace(".in",".out")
        fans = open(file_ans)
        fout = open(file_out)
        flag = lorun.check(fans.fileno(), fout.fileno())
        fout.close()
        fans.close()
        result['result'] = flag
    #os.remove(file_out)
    return result

def SPJudge(file_in,result):
    try:
        ret = int(os.popen('./spj '+file_in.replace(".in","")).read())
        if ret == 1:
            result['result'] = 0
        else:
            result['result'] = 4
    except BaseException as e:
        result['result'] = 8
    return result

if __name__ == '__main__':
    if len(sys.argv) != 8:
        print('error')
        exit(-1)
    res = judge(sys.argv[1].split('DOJ'),sys.argv[2],sys.argv[3],int(sys.argv[4]),int(sys.argv[5]),int(sys.argv[6]),int(sys.argv[7]))
    print(json.dumps(res))

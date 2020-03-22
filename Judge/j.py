import lorun
import os
import sys
import json

runcfg = {
    'args': [],
    'fd_in': 0,
    'fd_out': 0,
    'timelimit': 0,
    'memorylimit': 0,

}

def judge(cmd,file_in,file_out,time_limit,memory_limit):
    fin = open(file_in)
    fout = open(file_out,'w+')
    runcfg['args'] = cmd.split(' ')
    runcfg['fd_in'] = fin.fileno()
    runcfg['fd_out'] = fout.fileno()
    runcfg['timelimit'] = time_limit
    runcfg['memorylimit'] = memory_limit
    result = lorun.run(runcfg)
    fin.close()
    fout.close()
    if result['result'] == 0:
        file_ans = file_in.split('.')
        file_ans = file_ans[0] + '.out'
        fans = open(file_ans)
        fout = open(file_out)
        flag = lorun.check(fans.fileno(), fout.fileno())
        fout.close()
        fans.close()
        #os.remove(file_out)
        result['result'] = flag
    return result

#cmd in out time memory
if __name__ == '__main__':
    if len(sys.argv) != 6:
        print('error')
        exit(-1)
    res = judge(sys.argv[1],sys.argv[2],sys.argv[3],int(sys.argv[4]),int(sys.argv[5]))
    print(json.dumps(res))
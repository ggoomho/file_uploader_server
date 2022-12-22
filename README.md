# file_uploader_server
파일 업로더 서버


- Back End
    - 작업 환경: Visual Studio Code
    - 개발 언어: Java(1.7)
    - 사용 프레임워크
        - SpringBoot(2.7.6)
        - MariaDB
    - 개발 환경 세팅 관련 설명
        - vscode에서 개발하여 .vscode 디렉토리 아래에 launch.json 에서 실행 환경 변수 세팅 가능
        - 편의
            'application.yml'의 'ddl-auto: create' 옵션과 'createDatabaseIfNotExist=true'옵션은 test 환경의 db property로 사용하는 것이 일반적이지만
            과제 확인이 좀더 편할 수 있도록 src/main의 'application.ym'에도 해당옵션을 적용해 두었습니다. 
    - 구현에 대한 설명
        1. File Upload Request
            - Http POST 매서드를 통하여 File Upload 요청을 날립니다. (http://ip:port/file/upload)
            - file은 requestparam(key="file", type="FormData") 형식으로 전달합니다.
            - 10만건의 데이터를 저장하는데 시간이 걸리기 때문에 데이터를 저장하는 서비스는 async 동작을 하도록 하고 해당 요청에 대한 응답은
              요청한 파일의 정보(id 포함)을 넘기고 HttpStatus.OK 를 보냅니다.
                - HttpStatus.CREATED가 아니라 HttpStatus.OK를 보내는 이유는 생성 요청을 받았지만 아직 생성이 완료되지 않았기 때문에 요청을
                  성공적으로 확인했다는 의미로 HttpStatus.OK를 보냈습니다. 
            - 실제 데이터는 Batch Size(해당 프로젝트에서는 1000건) 단위로 DB에 Insert 작업을 하였습니다. 
                - 해당 옵션은 "application.yml" 파일의 'batch_size' 옵션에서 확인 가능합니다. 
        2. Progress 확인 방법
            - Progress를 확인하기 위해서 2가 방법을 생각했습니다. 
                1. SSE 방식
                    - SSE 방식은 서버에서 이벤트가 발생할때마다 client로 정보를 전달해주기 때문에 progress를 확인을 위한 기능 구현의 아키텍쳐로
                      적절하다고 생각했습니다. 하지만 과제의 요구사항이 json기반의 RestAPI 서버를 구현하는 것이기 때문에 이벤트 기반인 SSE는 
                      과제 요구사항에 맞지 않는다고 생각해서 polling 방식으로 구현하였습니다. 
                2. Polling 방식
                    - Front 에서 FileUpload를 요청하면 해당 파일에 대한 Id를 포함한 파일 정보를 서버에서 넘겨줍니다. (Progress complete 되기 전까지 0.5초 간격으로 호출)
                    - 파일의 upload progress를 확인하기 위해서 file id와 함께 progress 정보를 요청합니다. (http://ip:port/file/{fileId}/upload/progress)
                    - 서버에서는 Progress를 모니터링하기 위한 ProgressMonitor를 Bean객체로 가지고 있고 이 객체에는 파일별 progress 정보를 Map으로 가지고있습니다.
                    - 요청을 받은 서버는 해당 파일에 대한 Progress 정보를 응답합니다.

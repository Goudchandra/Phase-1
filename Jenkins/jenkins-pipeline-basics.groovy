pipeline {
    agent any
    stages {
        stage('Example') {
            steps {
                echo 'Hello World'
            }
        }
    }
}
// ======================
pipeline {
    agent any
    stages {
        stage('Example-1') {
            steps {
                echo 'Hello World-1'
            }
        }
		stage('Example-2') {
            steps {
                echo 'Hello World-2'
            }
        }
    }
}
// =============
pipeline {
    agent any
	parameters {
		choice(name: 'BUILD', choices: 'release\nsnapshot\n', description: 'Specify whether it is release or snapshot build.')
        string(name: 'UserName', defaultValue: 'venkat', description: 'Specify the git username.')
        string(name: 'Version', defaultValue: '1.0.0-SNAPSHOT', description: 'Specify the version.')
        password(name: 'PASSWORD', defaultValue: 'SECRET', description: 'Pasword')
		booleanParam(name: 'Deploy', defaultValue: false, description: 'Deploy or not')
		text(name: 'DEPLOY_Servers', defaultValue: 'One\nTwo\nThree\n', description: '')
		file(name: 'FILE', description: 'Some file to upload')
	}
    stages {
        stage('Example-1') {
            steps {
                echo 'Hello World-1'
				println "BUILD: ${params.BUILD}"
				println "UserName: ${params.UserName}"
				println "PASSWORD: ${params.PASSWORD}"
            }
        }
		stage('Example-2') {
            steps {
                echo 'Hello World-2'
				println "Version: ${params.Version}"
				println "Deploy: ${params.Deploy}"
				println "DEPLOY_Servers: ${params.DEPLOY_Servers}"
				println "FILE: ${params.FILE}"
            }
        }
    }
}
// ============
pipeline {
    agent any
    stages {
        stage('Example-1') {
            steps {
                echo 'Hello World-1'
            }
        }
		stage('Example-2') {
            steps {
                echo 'Hello World-2'
            }
        }
    }
    post { 
        always { 
            echo 'I will always say Hello again!'
        }
    }
}
//==============
pipeline {
    agent {
        label 'linux'
    }
	stages {
        stage('Stage-1') {
            steps {
                echo 'Executing from Linux'
            }
        }
    }
}
//==============
pipeline {
    agent {
        label 'windows'
    }
	stages {
        stage('Stage-1') {
            steps {
                echo 'Executing from windows'
            }
        }
    }
}
//===============
pipeline {
    agent {
        label "${params.BUILD_EXECUTOR}"
    }
	parameters {
    
        choice(name: 'BUILD_EXECUTOR', choices: 'linux\nwindows\n', description: 'Specify a Jenkins slave to execute the builds on.')
    }
	stages{
		stage("Stage-Linux"){
			when { expression { params.BUILD_EXECUTOR == 'linux' } }
			
			steps {
                echo 'Executing from linux'
				sh 'mkdir Linux-Dir-${BUILD_NUMBER}'
            }
		}
		stage("Stage-windows"){
			when { expression { params.BUILD_EXECUTOR == 'windows' } }
			
			steps {
                echo 'Executing from windows'
				bat 'mkdir Windows-Dir-%BUILD_NUMBER%'
            }
		}
	}
}

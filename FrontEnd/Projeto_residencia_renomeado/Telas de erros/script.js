 
        tailwind.config = {
            theme: {
                extend: {
                    colors: {
                        primary: {
                            light: '#8B5CF6',
                            DEFAULT: '#7C3AED',
                            dark: '#6D28D9',
                        },
                        secondary: {
                            light: '#60A5FA',
                            DEFAULT: '#3B82F6',
                            dark: '#2563EB',
                        },
                    },
                    animation: {
                        'float': 'float 3s ease-in-out infinite',
                        'pulse-slow': 'pulse 4s cubic-bezier(0.4, 0, 0.6, 1) infinite',
                        'bounce-slow': 'bounce 2s infinite',
                    },
                    keyframes: {
                        float: {
                            '0%, 100%': { transform: 'translateY(0)' },
                            '50%': { transform: 'translateY(-10px)' },
                        }
                    }
                }
            }
        }
    
        // Error configurations
        const errorConfigs = {
            '404': {
                title: 'Página não encontrada 😕',
                message: 'Não conseguimos encontrar a página que você está procurando. Ela pode ter sido movida ou não existe mais.',
                icon: `
                    <svg class="error-404-icon w-full h-full" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                        <path d="M12 22C17.5228 22 22 17.5228 22 12C22 6.47715 17.5228 2 12 2C6.47715 2 2 6.47715 2 12C2 17.5228 6.47715 22 12 22Z" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                        <path d="M15 9L9 15" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                        <path d="M9 9L15 15" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                    </svg>
                `,
                showLoginButton: false
            },
            '403': {
                title: 'Acesso negado 🔒',
                message: 'Você não tem permissão para acessar esta página. Se acredita que isso é um erro, entre em contato com o administrador.',
                icon: `
                    <svg class="error-403-icon w-full h-full" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                        <path d="M19 11H5C3.89543 11 3 11.8954 3 13V20C3 21.1046 3.89543 22 5 22H19C20.1046 22 21 21.1046 21 20V13C21 11.8954 20.1046 11 19 11Z" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                        <path d="M7 11V7C7 5.67392 7.52678 4.40215 8.46447 3.46447C9.40215 2.52678 10.6739 2 12 2C13.3261 2 14.5979 2.52678 15.5355 3.46447C16.4732 4.40215 17 5.67392 17 7V11" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                        <circle cx="12" cy="16" r="1" fill="currentColor"/>
                    </svg>
                `,
                showLoginButton: true
            },
            '500': {
                title: 'Erro interno 🛠️',
                message: 'Algo deu errado no nosso servidor. Nossa equipe técnica já foi notificada e está trabalhando para resolver o problema.',
                icon: `
                    <svg class="error-500-icon w-full h-full" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                        <path d="M10.29 3.86L1.82 18C1.64537 18.3024 1.55296 18.6453 1.55199 18.9945C1.55101 19.3437 1.6415 19.6871 1.81442 19.9905C1.98734 20.2939 2.23672 20.5467 2.53773 20.7238C2.83875 20.9009 3.1808 20.9961 3.53 21H20.47C20.8192 20.9961 21.1613 20.9009 21.4623 20.7238C21.7633 20.5467 22.0127 20.2939 22.1856 19.9905C22.3585 19.6871 22.449 19.3437 22.448 18.9945C22.447 18.6453 22.3546 18.3024 22.18 18L13.71 3.86C13.5317 3.56611 13.2807 3.32312 12.9812 3.15448C12.6817 2.98585 12.3437 2.89725 12 2.89725C11.6563 2.89725 11.3183 2.98585 11.0188 3.15448C10.7193 3.32312 10.4683 3.56611 10.29 3.86Z" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                        <path d="M12 9V13" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                        <path d="M12 17H12.01" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                    </svg>
                `,
                showLoginButton: false
            },
            'session': {
                title: 'Sessão expirada ⏰',
                message: 'Sua sessão expirou por inatividade. Por favor, faça login novamente para continuar usando o sistema.',
                icon: `
                    <svg class="error-session-icon w-full h-full" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                        <path d="M12 22C17.5228 22 22 17.5228 22 12C22 6.47715 17.5228 2 12 2C6.47715 2 2 6.47715 2 12C2 17.5228 6.47715 22 12 22Z" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                        <path d="M12 6V12L16 14" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                    </svg>
                `,
                showLoginButton: true
            }
        };

        // Function to update error content
        function updateErrorContent(errorType) {
            const config = errorConfigs[errorType];
            const iconContainer = document.querySelector('.error-icon');
            const title = document.querySelector('#error-content h1');
            const message = document.querySelector('#error-content p');
            const loginBtn = document.getElementById('login-btn');
            
            // Reset animations
            document.querySelectorAll('.fade-in, .slide-up').forEach(el => {
                el.style.animation = 'none';
                el.offsetHeight; // Trigger reflow
                el.style.animation = null;
            });
            
            // Update content
            iconContainer.innerHTML = config.icon;
            title.textContent = config.title;
            message.textContent = config.message;
            
            // Show/hide login button based on error type
            loginBtn.style.display = config.showLoginButton ? 'flex' : 'none';
            
            // Update active button in selector
            document.querySelectorAll('.error-selector').forEach(btn => {
                if (btn.dataset.error === errorType) {
                    btn.classList.remove('bg-gray-200', 'text-gray-700');
                    btn.classList.add('bg-primary', 'text-white');
                } else {
                    btn.classList.remove('bg-primary', 'text-white');
                    btn.classList.add('bg-gray-200', 'text-gray-700');
                }
            });
        }

        // Initialize with 404 error
        document.addEventListener('DOMContentLoaded', () => {
            updateErrorContent('404');
            
            // Add event listeners to error selectors
            document.querySelectorAll('.error-selector').forEach(btn => {
                btn.addEventListener('click', () => {
                    updateErrorContent(btn.dataset.error);
                });
            });
            
            // Add event listeners to buttons
            document.getElementById('home-btn').addEventListener('click', () => {
                alert('Redirecionando para a página inicial...');
            });
            
            document.getElementById('login-btn').addEventListener('click', () => {
                alert('Redirecionando para a página de login...');
            });
            
            document.getElementById('back-btn').addEventListener('click', () => {
                alert('Voltando para a página anterior...');
            });
        });
        
        // Function to check if URL has error parameter
        function checkUrlForError() {
            const urlParams = new URLSearchParams(window.location.search);
            const errorType = urlParams.get('error');
            if (errorType && errorConfigs[errorType]) {
                updateErrorContent(errorType);
            }
        }
        
        // Check URL for error parameter
        checkUrlForError();
 
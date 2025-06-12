
        tailwind.config = {
            theme: {
                extend: {
                    colors: {
                        primary: {
                            DEFAULT: '#6A2FD3',
                            light: '#8A5CE6',
                            dark: '#5020B0'
                        },
                        secondary: {
                            DEFAULT: '#3E7BFA',
                            light: '#6A9CFF',
                            dark: '#2A5CD9'
                        },
                        neutral: {
                            100: '#F8F9FC',
                            200: '#EEF1F8',
                            300: '#D8DEEB',
                            400: '#B0BBCF',
                            500: '#8896B2',
                            600: '#5D6B89',
                            700: '#404B66',
                            800: '#2D3648',
                            900: '#1A1F2C'
                        }
                    },
                    fontFamily: {
                        sans: ['Inter', 'sans-serif']
                    }
                }
            }
        }
   
        // Variáveis de controle
        let currentStep = 1;
        const totalSteps = 4;
        
        // Funções para navegação entre etapas
        function nextStep(step) {
            document.getElementById(`step-${step}`).classList.add('hidden');
            document.getElementById(`step-${step+1}`).classList.remove('hidden');
            currentStep = step + 1;
            updateProgress();
        }
        
        function prevStep(step) {
            document.getElementById(`step-${step}`).classList.add('hidden');
            document.getElementById(`step-${step-1}`).classList.remove('hidden');
            currentStep = step - 1;
            updateProgress();
        }
        
        function updateProgress() {
            // Atualiza os indicadores de progresso
            for (let i = 1; i <= totalSteps; i++) {
                const stepElement = document.querySelector(`.progress-step:nth-child(${i})`);
                if (i < currentStep) {
                    stepElement.classList.add('completed');
                    stepElement.classList.remove('active');
                } else if (i === currentStep) {
                    stepElement.classList.add('active');
                    stepElement.classList.remove('completed');
                } else {
                    stepElement.classList.remove('active', 'completed');
                }
            }
        }
        
        function showSummary() {
            document.getElementById(`step-${currentStep}`).classList.add('hidden');
            document.getElementById('summary').classList.remove('hidden');
        }
        
        function backToForm() {
            document.getElementById('summary').classList.add('hidden');
            document.getElementById(`step-${currentStep}`).classList.remove('hidden');
        }
        
        function submitForm() {
            // Mostrar estado de loading no botão
            const submitButton = document.getElementById('submit-button');
            const originalContent = submitButton.innerHTML;
            submitButton.innerHTML = `
                <svg class="animate-spin -ml-1 mr-2 h-5 w-5 text-white loading-spinner" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
                    <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4"></circle>
                    <path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"></path>
                </svg>
                <span>Processando...</span>
            `;
            submitButton.disabled = true;
            
            // Simular envio do formulário
            setTimeout(() => {
                document.getElementById('main-content').classList.add('hidden');
                document.getElementById('confirmation-screen').classList.remove('hidden');
            }, 2000);
        }
        
        // Funções para manipulação de arquivos
        function setupFileUpload() {
            const fileAreas = document.querySelectorAll('.file-drop-area');
            
            fileAreas.forEach(area => {
                const input = area.querySelector('input[type="file"]');
                const id = input.id;
                
                // Evento de clique na área
                area.addEventListener('click', () => {
                    input.click();
                });
                
                // Eventos de drag and drop
                area.addEventListener('dragover', (e) => {
                    e.preventDefault();
                    area.classList.add('active');
                });
                
                area.addEventListener('dragleave', () => {
                    area.classList.remove('active');
                });
                
                area.addEventListener('drop', (e) => {
                    e.preventDefault();
                    area.classList.remove('active');
                    
                    if (e.dataTransfer.files.length) {
                        input.files = e.dataTransfer.files;
                        updateFilePreview(id, e.dataTransfer.files[0]);
                    }
                });
                
                // Evento de mudança no input
                input.addEventListener('change', () => {
                    if (input.files.length) {
                        updateFilePreview(id, input.files[0]);
                    }
                });
            });
        }
        
        function updateFilePreview(id, file) {
            const preview = document.getElementById(`${id}-preview`);
            const fileName = preview.querySelector('.file-name');
            
            fileName.textContent = file.name;
            preview.classList.remove('hidden');
            document.getElementById(`${id}-area`).classList.add('hidden');
        }
        
        function removeFile(id) {
            const input = document.getElementById(id);
            input.value = '';
            document.getElementById(`${id}-preview`).classList.add('hidden');
            document.getElementById(`${id}-area`).classList.remove('hidden');
        }
        
        // Funções para seleção de data e hora
        function selectDate(element) {
            document.querySelectorAll('.calendar-day').forEach(day => {
                day.classList.remove('selected');
            });
            element.classList.add('selected');
        }
        
        function selectTime(element) {
            document.querySelectorAll('.time-slot').forEach(slot => {
                slot.classList.remove('selected');
            });
            element.classList.add('selected');
        }
        
        // Inicialização
        document.addEventListener('DOMContentLoaded', () => {
            setupFileUpload();
            updateProgress();
        });

document.addEventListener('DOMContentLoaded', function() {
            // Variáveis para controle de etapas
            const steps = document.querySelectorAll('.form-step');
            const progressBar = document.querySelector('.progress-bar');
            const progressText = document.getElementById('progress-text');
            const progressPercentage = document.getElementById('progress-percentage');
            let currentStep = 0;
            
            // Botões de navegação
            const nextButtons = document.querySelectorAll('.next-step');
            const prevButtons = document.querySelectorAll('.prev-step');
            
            // Formulário
            const form = document.getElementById('irpf-form');
            
            // Campos condicionais
            const possuiDependentesRadios = document.querySelectorAll('input[name="possui_dependentes"]');
            const dependentesContainer = document.getElementById('dependentes-container');
            const documentosDependentesContainer = document.getElementById('documentos-dependentes-container');
            
            const declarouAnteriorRadios = document.querySelectorAll('input[name="declarou_anterior"]');
            const reciboAnteriorContainer = document.getElementById('recibo-anterior-container');
            
            // Modal de sucesso
            const successModal = document.getElementById('success-modal');
            const closeModalButton = document.getElementById('close-modal');
            const protocoloSpan = document.getElementById('protocolo');
            
            // Função para atualizar a barra de progresso
            function updateProgress() {
                const totalSteps = steps.length;
                const progress = ((currentStep + 1) / totalSteps) * 100;
                progressBar.style.width = `${progress}%`;
                progressText.textContent = `Etapa ${currentStep + 1} de ${totalSteps}`;
                progressPercentage.textContent = `${Math.round(progress)}%`;
            }
            
            // Função para mostrar a etapa atual
            function showStep(stepIndex) {
                steps.forEach((step, index) => {
                    if (index === stepIndex) {
                        step.classList.add('active');
                    } else {
                        step.classList.remove('active');
                    }
                });
                
                updateProgress();
                window.scrollTo(0, 0);
            }
            
            // Função para validar a etapa atual
            function validateStep(stepIndex) {
                let isValid = true;
                const currentStepElement = steps[stepIndex];
                const requiredFields = currentStepElement.querySelectorAll('[required]');
                
                requiredFields.forEach(field => {
                    const errorElement = document.getElementById(`${field.id}-error`);
                    
                    if (field.type === 'radio') {
                        // Para campos de rádio, verificamos se algum do mesmo nome está marcado
                        const radioGroup = document.querySelectorAll(`input[name="${field.name}"]`);
                        const isChecked = Array.from(radioGroup).some(radio => radio.checked);
                        
                        if (!isChecked) {
                            isValid = false;
                            if (errorElement) {
                                errorElement.classList.remove('hidden');
                            }
                        } else if (errorElement) {
                            errorElement.classList.add('hidden');
                        }
                    } else if (field.type === 'file') {
                        // Para campos de arquivo, verificamos se há arquivos selecionados
                        if (field.files.length === 0) {
                            isValid = false;
                            if (errorElement) {
                                errorElement.classList.remove('hidden');
                            }
                        } else if (errorElement) {
                            errorElement.classList.add('hidden');
                        }
                    } else {
                        // Para outros campos, verificamos se estão vazios
                        if (!field.value.trim()) {
                            isValid = false;
                            field.classList.add('input-error');
                            if (errorElement) {
                                errorElement.classList.remove('hidden');
                            }
                        } else {
                            field.classList.remove('input-error');
                            if (errorElement) {
                                errorElement.classList.add('hidden');
                            }
                        }
                    }
                });
                
                return isValid;
            }
            
            // Evento para botões "Próximo"
            nextButtons.forEach(button => {
                button.addEventListener('click', () => {
                    if (validateStep(currentStep)) {
                        currentStep++;
                        if (currentStep < steps.length) {
                            showStep(currentStep);
                        }
                    }
                });
            });
            
            // Evento para botões "Anterior"
            prevButtons.forEach(button => {
                button.addEventListener('click', () => {
                    currentStep--;
                    if (currentStep >= 0) {
                        showStep(currentStep);
                    }
                });
            });
            
            // Evento para campos de dependentes
            possuiDependentesRadios.forEach(radio => {
                radio.addEventListener('change', () => {
                    if (radio.value === 'sim') {
                        dependentesContainer.classList.remove('hidden');
                        document.getElementById('qtd_dependentes').setAttribute('required', '');
                        documentosDependentesContainer.classList.remove('hidden');
                    } else {
                        dependentesContainer.classList.add('hidden');
                        document.getElementById('qtd_dependentes').removeAttribute('required');
                        documentosDependentesContainer.classList.add('hidden');
                    }
                });
            });
            
            // Evento para campos de declaração anterior
            declarouAnteriorRadios.forEach(radio => {
                radio.addEventListener('change', () => {
                    if (radio.value === 'sim') {
                        reciboAnteriorContainer.classList.remove('hidden');
                    } else {
                        reciboAnteriorContainer.classList.add('hidden');
                    }
                });
            });
            
            // Função para formatar CPF
            const cpfInput = document.getElementById('cpf');
            cpfInput.addEventListener('input', function(e) {
                let value = e.target.value.replace(/\D/g, '');
                if (value.length > 11) value = value.slice(0, 11);
                
                if (value.length > 9) {
                    value = value.replace(/^(\d{3})(\d{3})(\d{3})(\d{2})$/, '$1.$2.$3-$4');
                } else if (value.length > 6) {
                    value = value.replace(/^(\d{3})(\d{3})(\d{1,3})$/, '$1.$2.$3');
                } else if (value.length > 3) {
                    value = value.replace(/^(\d{3})(\d{1,3})$/, '$1.$2');
                }
                
                e.target.value = value;
            });
            
            // Função para formatar telefone
            const telefoneInput = document.getElementById('telefone');
            telefoneInput.addEventListener('input', function(e) {
                let value = e.target.value.replace(/\D/g, '');
                if (value.length > 11) value = value.slice(0, 11);
                
                if (value.length > 10) {
                    value = value.replace(/^(\d{2})(\d{5})(\d{4})$/, '($1) $2-$3');
                } else if (value.length > 6) {
                    value = value.replace(/^(\d{2})(\d{4})(\d{0,4})$/, '($1) $2-$3');
                } else if (value.length > 2) {
                    value = value.replace(/^(\d{2})(\d{0,5})$/, '($1) $2');
                }
                
                e.target.value = value;
            });
            
            // Função para formatar CEP
            const cepInput = document.getElementById('cep');
            cepInput.addEventListener('input', function(e) {
                let value = e.target.value.replace(/\D/g, '');
                if (value.length > 8) value = value.slice(0, 8);
                
                if (value.length > 5) {
                    value = value.replace(/^(\d{5})(\d{1,3})$/, '$1-$2');
                }
                
                e.target.value = value;
            });
            
            // Buscar CEP
            const buscarCepButton = document.getElementById('buscar-cep');
            buscarCepButton.addEventListener('click', function() {
                const cep = cepInput.value.replace(/\D/g, '');
                
                if (cep.length !== 8) {
                    alert('CEP inválido. Por favor, digite um CEP válido com 8 dígitos.');
                    return;
                }
                
                // Simulação de busca de CEP (em um ambiente real, seria uma chamada de API)
                setTimeout(() => {
                    // Dados fictícios para demonstração
                    document.getElementById('rua').value = 'Avenida Principal';
                    document.getElementById('bairro').value = 'Centro';
                    document.getElementById('cidade').value = 'Aracaju';
                    document.getElementById('estado').value = 'SE';
                    
                    // Foco no campo de número
                    document.getElementById('numero').focus();
                }, 1000);
            });
            
            // Gerenciar arquivos selecionados
            function handleFileSelection(fileInput, fileListContainer) {
                fileInput.addEventListener('change', function() {
                    const files = this.files;
                    fileListContainer.innerHTML = '';
                    
                    for (let i = 0; i < files.length; i++) {
                        const file = files[i];
                        const fileItem = document.createElement('div');
                        fileItem.className = 'file-item';
                        fileItem.innerHTML = `
                            <span class="text-sm">${file.name} (${(file.size / 1024).toFixed(1)} KB)</span>
                            <button type="button" class="text-red-500 hover:text-red-700">
                                <svg xmlns="http://www.w3.org/2000/svg" class="h-4 w-4" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12" />
                                </svg>
                            </button>
                        `;
                        fileListContainer.appendChild(fileItem);
                    }
                });
            }
            
            handleFileSelection(
                document.getElementById('comprovante_residencia'),
                document.getElementById('comprovante-residencia-list')
            );
            
            handleFileSelection(
                document.getElementById('informes_rendimentos'),
                document.getElementById('informes-rendimentos-list')
            );
            
            handleFileSelection(
                document.getElementById('documentos_dependentes'),
                document.getElementById('documentos-dependentes-list')
            );
            
            handleFileSelection(
                document.getElementById('recibo_anterior'),
                document.getElementById('recibo-anterior-list')
            );
            
            // Envio do formulário
            form.addEventListener('submit', function(e) {
                e.preventDefault();
                
                if (validateStep(currentStep)) {
                    // Simulação de envio do formulário
                    const submitButton = document.getElementById('submit-button');
                    submitButton.disabled = true;
                    submitButton.innerHTML = `
                        <svg class="animate-spin -ml-1 mr-2 h-5 w-5 text-white" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
                            <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4"></circle>
                            <path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"></path>
                        </svg>
                        Enviando...
                    `;
                    
                    setTimeout(() => {
                        // Gerar número de protocolo aleatório
                        const protocolo = `IRPF-${new Date().getFullYear()}-${Math.floor(Math.random() * 1000000).toString().padStart(6, '0')}`;
                        protocoloSpan.textContent = protocolo;
                        
                        // Mostrar modal de sucesso
                        successModal.classList.remove('hidden');
                        
                        // Resetar botão de envio
                        submitButton.disabled = false;
                        submitButton.innerHTML = `
                            <span>Enviar solicitação</span>
                            <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5 ml-1" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13 5l7 7-7 7M5 5l7 7-7 7" />
                            </svg>
                        `;
                    }, 2000);
                }
            });
            
            // Fechar modal de sucesso
            closeModalButton.addEventListener('click', function() {
                successModal.classList.add('hidden');
                form.reset();
                currentStep = 0;
                showStep(currentStep);
            });
            
            // Inicializar o formulário
            showStep(currentStep);
        });
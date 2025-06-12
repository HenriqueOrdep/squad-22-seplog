
        tailwind.config = {
            theme: {
                extend: {
                    colors: {
                        primary: {
                            50: '#f5f3ff',
                            100: '#ede9fe',
                            200: '#ddd6fe',
                            300: '#c4b5fd',
                            400: '#a78bfa',
                            500: '#8b5cf6',
                            600: '#7c3aed',
                            700: '#6d28d9',
                            800: '#5b21b6',
                            900: '#4c1d95',
                        },
                        secondary: {
                            50: '#eff6ff',
                            100: '#dbeafe',
                            200: '#bfdbfe',
                            300: '#93c5fd',
                            400: '#60a5fa',
                            500: '#3b82f6',
                            600: '#2563eb',
                            700: '#1d4ed8',
                            800: '#1e40af',
                            900: '#1e3a8a',
                        }
                    },
                    fontFamily: {
                        sans: ['Inter', 'sans-serif'],
                    }
                }
            }
        }
   
        // Máscaras e validações
        document.addEventListener('DOMContentLoaded', function() {
            // Toggle tipo de pessoa (física/jurídica)
            const personTypeRadios = document.querySelectorAll('input[name="personType"]');
            const documentInput = document.getElementById('document');
            const nameLabel = document.querySelector('label[for="name"]');
            
            personTypeRadios.forEach(radio => {
                radio.addEventListener('change', function() {
                    if (this.value === 'fisica') {
                        documentInput.placeholder = '000.000.000-00';
                        nameLabel.textContent = 'Nome Completo';
                    } else {
                        documentInput.placeholder = '00.000.000/0000-00';
                        nameLabel.textContent = 'Razão Social';
                    }
                    // Limpar o campo quando mudar o tipo
                    documentInput.value = '';
                });
            });
            
            // Máscara para CPF/CNPJ
            documentInput.addEventListener('input', function(e) {
                const personType = document.querySelector('input[name="personType"]:checked').value;
                let value = e.target.value.replace(/\D/g, '');
                
                if (personType === 'fisica') {
                    // Máscara de CPF: 000.000.000-00
                    if (value.length <= 11) {
                        value = value.replace(/(\d{3})(\d)/, '$1.$2');
                        value = value.replace(/(\d{3})(\d)/, '$1.$2');
                        value = value.replace(/(\d{3})(\d{1,2})$/, '$1-$2');
                    }
                } else {
                    // Máscara de CNPJ: 00.000.000/0000-00
                    if (value.length <= 14) {
                        value = value.replace(/(\d{2})(\d)/, '$1.$2');
                        value = value.replace(/(\d{3})(\d)/, '$1.$2');
                        value = value.replace(/(\d{3})(\d)/, '$1/$2');
                        value = value.replace(/(\d{4})(\d{1,2})$/, '$1-$2');
                    }
                }
                
                e.target.value = value;
            });
            
            // Máscara para CEP
            const cepInput = document.getElementById('cep');
            cepInput.addEventListener('input', function(e) {
                let value = e.target.value.replace(/\D/g, '');
                if (value.length <= 8) {
                    value = value.replace(/(\d{5})(\d)/, '$1-$2');
                }
                e.target.value = value;
            });
            
            // Máscara para telefone fixo
            const phoneInput = document.getElementById('phone');
            phoneInput.addEventListener('input', function(e) {
                let value = e.target.value.replace(/\D/g, '');
                if (value.length <= 10) {
                    value = value.replace(/(\d{2})(\d)/, '($1) $2');
                    value = value.replace(/(\d{4})(\d)/, '$1-$2');
                }
                e.target.value = value;
            });
            
            // Máscara para celular
            const mobileInput = document.getElementById('mobile');
            mobileInput.addEventListener('input', function(e) {
                let value = e.target.value.replace(/\D/g, '');
                if (value.length <= 11) {
                    value = value.replace(/(\d{2})(\d)/, '($1) $2');
                    value = value.replace(/(\d{5})(\d)/, '$1-$2');
                }
                e.target.value = value;
            });
            
            // Busca de CEP
            const searchCepBtn = document.getElementById('search-cep');
            const addressInput = document.getElementById('address');
            const neighborhoodInput = document.getElementById('neighborhood');
            const cityInput = document.getElementById('city');
            const stateInput = document.getElementById('state');
            const cepError = document.getElementById('cep-error');
            
            searchCepBtn.addEventListener('click', function() {
                const cep = cepInput.value.replace(/\D/g, '');
                
                if (cep.length !== 8) {
                    cepError.classList.remove('hidden');
                    return;
                }
                
                // Simulação de busca de CEP (em um ambiente real, seria uma chamada de API)
                // Aqui estamos apenas simulando para demonstração
                searchCepBtn.innerHTML = `
                    <svg class="animate-spin h-5 w-5 text-white" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
                        <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4"></circle>
                        <path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"></path>
                    </svg>
                `;
                
                setTimeout(() => {
                    // Simulando dados de endereço
                    if (cep === '49000000') {
                        addressInput.value = 'Avenida Barão de Maruim';
                        neighborhoodInput.value = 'Centro';
                        cityInput.value = 'Aracaju';
                        stateInput.value = 'SE';
                        cepError.classList.add('hidden');
                    } else if (cep === '49010000') {
                        addressInput.value = 'Rua João Pessoa';
                        neighborhoodInput.value = 'Centro';
                        cityInput.value = 'Aracaju';
                        stateInput.value = 'SE';
                        cepError.classList.add('hidden');
                    } else {
                        cepError.classList.remove('hidden');
                        addressInput.value = '';
                        neighborhoodInput.value = '';
                        cityInput.value = '';
                        stateInput.value = '';
                    }
                    
                    searchCepBtn.innerHTML = 'Buscar';
                }, 1000);
            });
            
            // Toggle de visibilidade da senha
            const togglePasswordButtons = document.querySelectorAll('.toggle-password');
            
            togglePasswordButtons.forEach(button => {
                button.addEventListener('click', function() {
                    const input = this.previousElementSibling;
                    const type = input.getAttribute('type') === 'password' ? 'text' : 'password';
                    input.setAttribute('type', type);
                    
                    // Alterna o ícone
                    const eyeIcon = this.querySelector('svg');
                    if (type === 'text') {
                        eyeIcon.innerHTML = `
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13.875 18.825A10.05 10.05 0 0112 19c-4.478 0-8.268-2.943-9.543-7a9.97 9.97 0 011.563-3.029m5.858.908a3 3 0 114.243 4.243M9.878 9.878l4.242 4.242M9.88 9.88l-3.29-3.29m7.532 7.532l3.29 3.29M3 3l3.59 3.59m0 0A9.953 9.953 0 0112 5c4.478 0 8.268 2.943 9.543 7a10.025 10.025 0 01-4.132 5.411m0 0L21 21" />
                        `;
                    } else {
                        eyeIcon.innerHTML = `
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 12a3 3 0 11-6 0 3 3 0 016 0z" />
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M2.458 12C3.732 7.943 7.523 5 12 5c4.478 0 8.268 2.943 9.542 7-1.274 4.057-5.064 7-9.542 7-4.477 0-8.268-2.943-9.542-7z" />
                        `;
                    }
                });
            });
            
            // Validação de força da senha
            const passwordInput = document.getElementById('password');
            const confirmPasswordInput = document.getElementById('confirm-password');
            const passwordError = document.getElementById('password-error');
            const strengthBars = [
                document.getElementById('strength-1'),
                document.getElementById('strength-2'),
                document.getElementById('strength-3'),
                document.getElementById('strength-4')
            ];
            const strengthText = document.getElementById('strength-text');
            
            // Regras de senha
            const ruleLength = document.getElementById('rule-length');
            const ruleUppercase = document.getElementById('rule-uppercase');
            const ruleLowercase = document.getElementById('rule-lowercase');
            const ruleNumber = document.getElementById('rule-number');
            const ruleSpecial = document.getElementById('rule-special');
            
            passwordInput.addEventListener('input', function() {
                const password = this.value;
                let strength = 0;
                
                // Verificar comprimento
                const hasLength = password.length >= 8;
                updateRule(ruleLength, hasLength);
                if (hasLength) strength++;
                
                // Verificar letra maiúscula
                const hasUppercase = /[A-Z]/.test(password);
                updateRule(ruleUppercase, hasUppercase);
                if (hasUppercase) strength++;
                
                // Verificar letra minúscula
                const hasLowercase = /[a-z]/.test(password);
                updateRule(ruleLowercase, hasLowercase);
                if (hasLowercase) strength++;
                
                // Verificar número
                const hasNumber = /\d/.test(password);
                updateRule(ruleNumber, hasNumber);
                if (hasNumber) strength++;
                
                // Verificar caractere especial
                const hasSpecial = /[!@#$%^&*(),.?":{}|<>]/.test(password);
                updateRule(ruleSpecial, hasSpecial);
                if (hasSpecial) strength++;
                
                // Atualizar barras de força
                updateStrengthBars(strength);
                
                // Verificar se as senhas coincidem
                if (confirmPasswordInput.value && confirmPasswordInput.value !== password) {
                    passwordError.classList.remove('hidden');
                    confirmPasswordInput.parentElement.classList.add('input-error');
                } else {
                    passwordError.classList.add('hidden');
                    confirmPasswordInput.parentElement.classList.remove('input-error');
                }
            });
            
            confirmPasswordInput.addEventListener('input', function() {
                if (this.value !== passwordInput.value) {
                    passwordError.classList.remove('hidden');
                    this.parentElement.classList.add('input-error');
                } else {
                    passwordError.classList.add('hidden');
                    this.parentElement.classList.remove('input-error');
                }
            });
            
            function updateRule(ruleElement, isValid) {
                if (isValid) {
                    ruleElement.classList.remove('text-gray-500');
                    ruleElement.classList.add('text-green-500');
                    ruleElement.querySelector('svg').innerHTML = `
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7" />
                    `;
                } else {
                    ruleElement.classList.remove('text-green-500');
                    ruleElement.classList.add('text-gray-500');
                    ruleElement.querySelector('svg').innerHTML = `
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z" />
                    `;
                }
            }
            
            function updateStrengthBars(strength) {
                // Resetar todas as barras
                strengthBars.forEach(bar => {
                    bar.className = 'h-2 flex-1 rounded-full bg-gray-200';
                });
                
                // Definir cores com base na força
                let color = 'bg-red-500';
                let message = 'Senha fraca';
                
                if (strength >= 5) {
                    color = 'bg-green-500';
                    message = 'Senha forte';
                } else if (strength >= 3) {
                    color = 'bg-yellow-500';
                    message = 'Senha média';
                } else if (strength >= 1) {
                    color = 'bg-red-500';
                    message = 'Senha fraca';
                }
                
                // Atualizar barras de acordo com a força
                for (let i = 0; i < strength && i < 4; i++) {
                    strengthBars[i].className = `h-2 flex-1 rounded-full ${color}`;
                }
                
                // Atualizar texto
                strengthText.textContent = message;
                strengthText.className = `text-sm mt-1 ${strength >= 5 ? 'text-green-600' : strength >= 3 ? 'text-yellow-600' : 'text-red-600'}`;
            }
            
            // Validação de e-mail
            const emailInput = document.getElementById('email');
            const emailError = document.getElementById('email-error');
            
            emailInput.addEventListener('blur', function() {
                const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
                if (!emailRegex.test(this.value) && this.value !== '') {
                    emailError.classList.remove('hidden');
                    this.parentElement.classList.add('input-error');
                } else {
                    emailError.classList.add('hidden');
                    this.parentElement.classList.remove('input-error');
                }
            });
            
            // Validação de documento (CPF/CNPJ)
            const documentError = document.getElementById('document-error');
            
            documentInput.addEventListener('blur', function() {
                const personType = document.querySelector('input[name="personType"]:checked').value;
                const value = this.value.replace(/\D/g, '');
                
                let isValid = false;
                
                if (personType === 'fisica') {
                    isValid = validateCPF(value);
                } else {
                    isValid = validateCNPJ(value);
                }
                
                if (!isValid && this.value !== '') {
                    documentError.classList.remove('hidden');
                    this.parentElement.classList.add('input-error');
                } else {
                    documentError.classList.add('hidden');
                    this.parentElement.classList.remove('input-error');
                }
            });
            
            function validateCPF(cpf) {
                if (cpf.length !== 11) return false;
                
                // Verifica se todos os dígitos são iguais
                if (/^(\d)\1+$/.test(cpf)) return false;
                
                // Validação do primeiro dígito verificador
                let sum = 0;
                for (let i = 0; i < 9; i++) {
                    sum += parseInt(cpf.charAt(i)) * (10 - i);
                }
                let remainder = 11 - (sum % 11);
                if (remainder === 10 || remainder === 11) remainder = 0;
                if (remainder !== parseInt(cpf.charAt(9))) return false;
                
                // Validação do segundo dígito verificador
                sum = 0;
                for (let i = 0; i < 10; i++) {
                    sum += parseInt(cpf.charAt(i)) * (11 - i);
                }
                remainder = 11 - (sum % 11);
                if (remainder === 10 || remainder === 11) remainder = 0;
                if (remainder !== parseInt(cpf.charAt(10))) return false;
                
                return true;
            }
            
            function validateCNPJ(cnpj) {
                if (cnpj.length !== 14) return false;
                
                // Verifica se todos os dígitos são iguais
                if (/^(\d)\1+$/.test(cnpj)) return false;
                
                // Validação do primeiro dígito verificador
                let size = cnpj.length - 2;
                let numbers = cnpj.substring(0, size);
                const digits = cnpj.substring(size);
                let sum = 0;
                let pos = size - 7;
                
                for (let i = size; i >= 1; i--) {
                    sum += numbers.charAt(size - i) * pos--;
                    if (pos < 2) pos = 9;
                }
                
                let result = sum % 11 < 2 ? 0 : 11 - sum % 11;
                if (result !== parseInt(digits.charAt(0))) return false;
                
                // Validação do segundo dígito verificador
                size = size + 1;
                numbers = cnpj.substring(0, size);
                sum = 0;
                pos = size - 7;
                
                for (let i = size; i >= 1; i--) {
                    sum += numbers.charAt(size - i) * pos--;
                    if (pos < 2) pos = 9;
                }
                
                result = sum % 11 < 2 ? 0 : 11 - sum % 11;
                if (result !== parseInt(digits.charAt(1))) return false;
                
                return true;
            }
            
            // Formulário de cadastro
            const registrationForm = document.getElementById('registration-form');
            const successModal = document.getElementById('success-modal');
            const closeSuccessBtn = document.getElementById('close-success');
            const cancelBtn = document.getElementById('cancel-btn');
            
            registrationForm.addEventListener('submit', function(e) {
                e.preventDefault();
                
                // Validação do formulário
                let isValid = true;
                
                // Validar e-mail
                const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
                if (!emailRegex.test(emailInput.value)) {
                    emailError.classList.remove('hidden');
                    emailInput.parentElement.classList.add('input-error');
                    isValid = false;
                }
                
                // Validar documento
                const personType = document.querySelector('input[name="personType"]:checked').value;
                const documentValue = documentInput.value.replace(/\D/g, '');
                
                let isDocumentValid = false;
                if (personType === 'fisica') {
                    isDocumentValid = validateCPF(documentValue);
                } else {
                    isDocumentValid = validateCNPJ(documentValue);
                }
                
                if (!isDocumentValid) {
                    documentError.classList.remove('hidden');
                    documentInput.parentElement.classList.add('input-error');
                    isValid = false;
                }
                
                // Validar senha
                const password = passwordInput.value;
                const confirmPassword = confirmPasswordInput.value;
                
                if (password !== confirmPassword) {
                    passwordError.classList.remove('hidden');
                    confirmPasswordInput.parentElement.classList.add('input-error');
                    isValid = false;
                }
                
                const hasLength = password.length >= 8;
                const hasUppercase = /[A-Z]/.test(password);
                const hasLowercase = /[a-z]/.test(password);
                const hasNumber = /\d/.test(password);
                const hasSpecial = /[!@#$%^&*(),.?":{}|<>]/.test(password);
                
                if (!hasLength || !hasUppercase || !hasLowercase || !hasNumber || !hasSpecial) {
                    passwordInput.parentElement.classList.add('input-error');
                    isValid = false;
                }
                
                if (isValid) {
                    // Simulação de envio bem-sucedido
                    successModal.classList.remove('hidden');
                }
            });
            
            closeSuccessBtn.addEventListener('click', function() {
                successModal.classList.add('hidden');
                // Aqui você redirecionaria o usuário ou faria outras ações necessárias
            });
            
            cancelBtn.addEventListener('click', function() {
                if (confirm('Deseja realmente cancelar o cadastro? Todos os dados preenchidos serão perdidos.')) {
                    window.location.href = '#'; // Redirecionar para a página inicial ou de login
                }
            });
            
            // Fechar modal ao clicar fora
            window.addEventListener('click', function(e) {
                if (e.target === successModal) {
                    successModal.classList.add('hidden');
                }
            });
        });
    
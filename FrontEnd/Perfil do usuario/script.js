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
                        tertiary: {
                            light: '#C4B5FD',
                            DEFAULT: '#A78BFA',
                            dark: '#8B5CF6',
                        },
                    },
                    fontFamily: {
                        sans: ['Inter', 'sans-serif'],
                    },
                }
            }
        }

        // Menu navigation
        document.querySelectorAll('.menu-item').forEach(item => {
            item.addEventListener('click', function(e) {
                e.preventDefault();
                
                // Remove active class from all menu items
                document.querySelectorAll('.menu-item').forEach(el => {
                    el.classList.remove('active');
                });
                
                // Add active class to clicked item
                this.classList.add('active');
                
                // Hide all sections first
                document.querySelectorAll('section').forEach(section => {
                    section.classList.add('hidden');
                });
                
                // Show corresponding section
                const href = this.getAttribute('href');
                if (href === '#') {
                    // Home - show profile sections
                    document.getElementById('personal-data-section').classList.remove('hidden');
                    document.getElementById('address-section').classList.remove('hidden');
                    document.getElementById('security-section').classList.remove('hidden');
                    window.scrollTo({top: 0, behavior: 'smooth'});
                } else {
                    const sectionId = href.substring(1);
                    const section = document.getElementById(sectionId);
                    if (section) {
                        section.classList.remove('hidden');
                        section.scrollIntoView({behavior: 'smooth'});
                    }
                }
            });
        });

        // Set initial active menu item (Profile)
        document.addEventListener('DOMContentLoaded', function() {
            const profileLink = document.querySelector('a[href="#personal-data-section"]');
            if (profileLink) {
                profileLink.classList.add('active');
            }
            
            // Hide sections that are not profile related
            document.getElementById('services-section').classList.add('hidden');
            document.getElementById('appointments-section').classList.add('hidden');
            document.getElementById('help-section').classList.add('hidden');
        });

        // Toggle password visibility
        document.querySelectorAll('.toggle-password').forEach(button => {
            button.addEventListener('click', function() {
                const input = this.parentElement.querySelector('input');
                const icon = this.querySelector('i');
                
                if (input.type === 'password') {
                    input.type = 'text';
                    icon.classList.remove('fa-eye');
                    icon.classList.add('fa-eye-slash');
                } else {
                    input.type = 'password';
                    icon.classList.remove('fa-eye-slash');
                    icon.classList.add('fa-eye');
                }
            });
        });
        
        // Show save button only when form changes
        const personalDataForm = document.getElementById('personal-data-form');
        const savePersonalDataBtn = document.getElementById('savePersonalData');
        
        const formInputs = personalDataForm.querySelectorAll('input, select');
        let initialFormState = {};
        
        // Store initial form state
        formInputs.forEach(input => {
            if (input.type === 'radio') {
                if (!initialFormState[input.name]) initialFormState[input.name] = {};
                initialFormState[input.name][input.value] = input.checked;
            } else {
                initialFormState[input.name] = input.value;
            }
        });
        
        // Check for changes
        formInputs.forEach(input => {
            input.addEventListener('input', checkFormChanges);
        });
        
        function checkFormChanges() {
            let hasChanges = false;
            
            formInputs.forEach(input => {
                if (input.type === 'radio') {
                    if (initialFormState[input.name][input.value] !== input.checked) {
                        hasChanges = true;
                    }
                } else if (initialFormState[input.name] !== input.value) {
                    hasChanges = true;
                }
            });
            
            if (hasChanges) {
                savePersonalDataBtn.classList.remove('hidden');
            } else {
                savePersonalDataBtn.classList.add('hidden');
            }
        }
        
        // CEP search functionality
        document.getElementById('searchZipCode').addEventListener('click', function() {
            const cep = document.getElementById('zipCode').value.replace(/\D/g, '');
            
            if (cep.length !== 8) {
                alert('Por favor, digite um CEP válido com 8 dígitos.');
                return;
            }
            
            // Simulate CEP search (in a real app, this would be an API call)
            // For demo purposes, we'll just fill with sample data
            setTimeout(() => {
                document.getElementById('street').value = 'Avenida Desembargador Maynard';
                document.getElementById('neighborhood').value = 'Cirurgia';
                document.getElementById('city').value = 'Aracaju';
                document.getElementById('state').value = 'SE';
                
                // Focus on number field for better UX
                document.getElementById('number').focus();
            }, 500);
        });
        
        // Form submissions
        document.querySelectorAll('form').forEach(form => {
            form.addEventListener('submit', function(e) {
                e.preventDefault();
                
                // Show success message
                const formId = this.id;
                let message = '';
                
                if (formId === 'personal-data-form') {
                    message = 'Dados pessoais atualizados com sucesso!';
                    savePersonalDataBtn.classList.add('hidden');
                } else if (formId === 'address-form') {
                    message = 'Endereço atualizado com sucesso!';
                } else if (formId === 'security-form') {
                    message = 'Senha atualizada com sucesso!';
                    this.reset();
                }
                
                // Create and show toast notification
                const toast = document.createElement('div');
                toast.className = 'fixed bottom-4 right-4 bg-green-500 text-white px-6 py-3 rounded-lg shadow-lg flex items-center z-50';
                toast.innerHTML = `
                    <i class="fas fa-check-circle mr-2"></i>
                    <span>${message}</span>
                `;
                
                document.body.appendChild(toast);
                
                // Remove toast after 3 seconds
                setTimeout(() => {
                    toast.classList.add('opacity-0', 'transition-opacity', 'duration-500');
                    setTimeout(() => {
                        document.body.removeChild(toast);
                    }, 500);
                }, 3000);
            });
        });
        
        // Responsive behavior for mobile
        function checkScreenSize() {
            const sidebar = document.querySelector('.sidebar');
            const mainContent = document.querySelector('.main-content');
            
            if (window.innerWidth <= 768) {
                mainContent.classList.remove('ml-64');
                mainContent.classList.add('ml-0');
            } else {
                mainContent.classList.remove('ml-0');
                mainContent.classList.add('ml-64');
            }
        }
        
        // Check on load and resize
        window.addEventListener('load', checkScreenSize);
        window.addEventListener('resize', checkScreenSize);

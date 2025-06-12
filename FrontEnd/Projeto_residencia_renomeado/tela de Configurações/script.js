
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
   
        // Mobile menu toggle
        const mobileMenuButton = document.getElementById('mobile-menu-button');
        const mobileMenu = document.getElementById('mobile-menu');
        
        mobileMenuButton.addEventListener('click', () => {
            mobileMenu.classList.toggle('hidden');
        });
        
        // SMS checkbox toggle phone input
        const smsCheckbox = document.getElementById('sms-reminders');
        const phoneInputContainer = document.getElementById('phone-input-container');
        
        smsCheckbox.addEventListener('change', () => {
            if (smsCheckbox.checked) {
                phoneInputContainer.style.display = 'block';
            } else {
                phoneInputContainer.style.display = 'none';
            }
        });
        
        // Theme selection
        const lightTheme = document.getElementById('light-theme');
        const darkTheme = document.getElementById('dark-theme');
        const systemTheme = document.getElementById('system-theme');
        const body = document.getElementById('body');
        
        function setTheme(theme) {
            // Remove borders from all theme options
            document.querySelectorAll('.theme-option div').forEach(el => {
                el.classList.remove('border-primary');
                el.classList.add('border-transparent');
            });
            
            // Add border to selected theme
            document.querySelector(`[data-theme="${theme}"]`).classList.remove('border-transparent');
            document.querySelector(`[data-theme="${theme}"]`).classList.add('border-primary');
            
            // Apply theme
            if (theme === 'dark') {
                body.classList.add('dark');
                localStorage.setItem('theme', 'dark');
            } else if (theme === 'light') {
                body.classList.remove('dark');
                localStorage.setItem('theme', 'light');
            } else if (theme === 'system') {
                const prefersDark = window.matchMedia('(prefers-color-scheme: dark)').matches;
                if (prefersDark) {
                    body.classList.add('dark');
                } else {
                    body.classList.remove('dark');
                }
                localStorage.setItem('theme', 'system');
            }
        }
        
        // Initialize theme
        const savedTheme = localStorage.getItem('theme') || 'light';
        setTheme(savedTheme);
        
        lightTheme.addEventListener('click', () => setTheme('light'));
        darkTheme.addEventListener('click', () => setTheme('dark'));
        systemTheme.addEventListener('click', () => setTheme('system'));
        
        // Font size preview
        const fontSizeSelect = document.getElementById('font-size');
        const fontPreview = document.getElementById('font-preview');
        
        fontSizeSelect.addEventListener('change', () => {
            const fontSize = fontSizeSelect.value;
            fontPreview.className = `text-gray-600 font-size-${fontSize}`;
        });
        
        // Initialize font size
        const savedFontSize = localStorage.getItem('fontSize') || 'medium';
        fontSizeSelect.value = savedFontSize;
        fontPreview.className = `text-gray-600 font-size-${savedFontSize}`;
        
        // Form submissions
        const notificationForm = document.getElementById('notification-form');
        const appearanceForm = document.getElementById('appearance-form');
        const privacyForm = document.getElementById('privacy-form');
        const successToast = document.getElementById('success-toast');
        const toastMessage = document.getElementById('toast-message');
        const closeToast = document.getElementById('close-toast');
        
        function showToast(message) {
            toastMessage.textContent = message;
            successToast.classList.remove('hidden');
            setTimeout(() => {
                successToast.classList.add('hidden');
            }, 3000);
        }
        
        closeToast.addEventListener('click', () => {
            successToast.classList.add('hidden');
        });
        
        notificationForm.addEventListener('submit', (e) => {
            e.preventDefault();
            showToast('Preferências de notificação salvas com sucesso!');
        });
        
        appearanceForm.addEventListener('submit', (e) => {
            e.preventDefault();
            localStorage.setItem('fontSize', fontSizeSelect.value);
            showToast('Preferências de aparência salvas com sucesso!');
        });
        
        privacyForm.addEventListener('submit', (e) => {
            e.preventDefault();
            showToast('Configurações de privacidade atualizadas com sucesso!');
        });
        
        // Deactivate account modal
        const deactivateBtn = document.getElementById('deactivate-account-btn');
        const deactivateModal = document.getElementById('deactivate-modal');
        const deactivateCancel = document.getElementById('deactivate-cancel');
        const deactivateConfirm = document.getElementById('deactivate-confirm');
        
        deactivateBtn.addEventListener('click', () => {
            deactivateModal.classList.remove('hidden');
        });
        
        deactivateCancel.addEventListener('click', () => {
            deactivateModal.classList.add('hidden');
        });
        
        deactivateConfirm.addEventListener('click', () => {
            deactivateModal.classList.add('hidden');
            showToast('Sua conta foi desativada temporariamente.');
        });
        
        // Delete account modal
        const deleteBtn = document.getElementById('delete-account-btn');
        const deleteModal = document.getElementById('delete-modal');
        const deleteCancel = document.getElementById('delete-cancel');
        const deleteConfirm = document.getElementById('delete-confirm');
        const deleteConfirmation = document.getElementById('delete-confirmation');
        
        deleteBtn.addEventListener('click', () => {
            deleteModal.classList.remove('hidden');
        });
        
        deleteCancel.addEventListener('click', () => {
            deleteModal.classList.add('hidden');
            deleteConfirmation.value = '';
        });
        
        deleteConfirmation.addEventListener('input', () => {
            if (deleteConfirmation.value === 'EXCLUIR') {
                deleteConfirm.classList.remove('opacity-50', 'cursor-not-allowed');
                deleteConfirm.disabled = false;
            } else {
                deleteConfirm.classList.add('opacity-50', 'cursor-not-allowed');
                deleteConfirm.disabled = true;
            }
        });
        
        deleteConfirm.addEventListener('click', () => {
            if (deleteConfirmation.value === 'EXCLUIR') {
                deleteModal.classList.add('hidden');
                showToast('Sua conta foi excluída permanentemente.');
            }
        });
        
        // Close modals when clicking outside
        window.addEventListener('click', (e) => {
            if (e.target === deactivateModal) {
                deactivateModal.classList.add('hidden');
            }
            if (e.target === deleteModal) {
                deleteModal.classList.add('hidden');
                deleteConfirmation.value = '';
            }
        });
        
        // Settings navigation
        const settingsNavItems = document.querySelectorAll('.settings-nav-item');
        
        settingsNavItems.forEach(item => {
            item.addEventListener('click', (e) => {
                // Remove active class from all items
                settingsNavItems.forEach(navItem => {
                    navItem.classList.remove('text-primary', 'bg-primary', 'bg-opacity-10');
                    navItem.classList.add('text-gray-600', 'hover:text-primary', 'hover:bg-primary', 'hover:bg-opacity-10');
                });
                
                // Add active class to clicked item
                item.classList.remove('text-gray-600', 'hover:text-primary', 'hover:bg-primary', 'hover:bg-opacity-10');
                item.classList.add('text-primary', 'bg-primary', 'bg-opacity-10');
            });
        });
    
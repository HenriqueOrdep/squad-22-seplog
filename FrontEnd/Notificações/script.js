 
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
        
        // Filter toggle for mobile
        const filterToggle = document.getElementById('filter-toggle');
        const mobileFilters = document.getElementById('mobile-filters');
        
        filterToggle.addEventListener('click', () => {
            mobileFilters.classList.toggle('show');
        });
        
        // Notification cards click
        const notificationCards = document.querySelectorAll('.notification-card');
        const notificationModal = document.getElementById('notification-modal');
        const closeModal = document.getElementById('close-modal');
        const modalTitle = document.getElementById('modal-title');
        const modalSubtitle = document.getElementById('modal-subtitle');
        const modalDate = document.getElementById('modal-date');
        const modalDescription = document.getElementById('modal-description');
        const modalIcon = document.getElementById('modal-icon');
        const actionButton = document.getElementById('action-button');
        
        notificationCards.forEach(card => {
            card.addEventListener('click', () => {
                // Get notification data
                const id = card.getAttribute('data-id');
                const title = card.querySelector('h3').textContent;
                const date = card.querySelector('.text-xs').textContent.trim();
                const description = card.querySelector('p').textContent;
                const isUnread = card.classList.contains('unread');
                
                // Set modal content
                modalTitle.textContent = 'Detalhes da Notificação';
                modalSubtitle.textContent = title;
                modalDate.textContent = date;
                
                // Set icon based on notification type
                if (title.includes('Agendamento')) {
                    modalIcon.innerHTML = '<i class="fas fa-calendar-alt"></i>';
                    modalIcon.className = 'w-10 h-10 rounded-full bg-primary-light bg-opacity-20 flex items-center justify-center text-primary';
                    actionButton.textContent = 'Ver agendamento';
                } else if (title.includes('Documento')) {
                    modalIcon.innerHTML = '<i class="fas fa-file-alt"></i>';
                    modalIcon.className = 'w-10 h-10 rounded-full bg-secondary-light bg-opacity-20 flex items-center justify-center text-secondary';
                    actionButton.textContent = 'Ver documento';
                } else {
                    modalIcon.innerHTML = '<i class="fas fa-exclamation-triangle"></i>';
                    modalIcon.className = 'w-10 h-10 rounded-full bg-yellow-100 flex items-center justify-center text-yellow-600';
                    actionButton.textContent = 'Mais informações';
                }
                
                // Set detailed content based on notification type
                if (title.includes('Agendamento Confirmado')) {
                    modalDescription.innerHTML = `
                        <p>${description}</p>
                        <p class="mt-2">Local: Unidade Central - Av. Principal, 1000</p>
                        <p class="mt-2">Documentos necessários:</p>
                        <ul>
                            <li>Documento de identidade com foto</li>
                            <li>CPF</li>
                            <li>Comprovante de residência</li>
                        </ul>
                        <p class="mt-2">Observações:</p>
                        <p>Chegue com 15 minutos de antecedência. Em caso de impossibilidade de comparecimento, cancele com pelo menos 24h de antecedência.</p>
                    `;
                } else if (title.includes('Documento Disponível')) {
                    modalDescription.innerHTML = `
                        <p>${description}</p>
                        <p class="mt-2">Tipo de documento: Certidão Negativa de Débitos</p>
                        <p class="mt-2">Validade: 90 dias a partir da emissão</p>
                        <p class="mt-2">Você pode baixar o documento em formato PDF ou solicitar uma cópia impressa em uma das unidades de atendimento.</p>
                    `;
                } else if (title.includes('Alerta de Manutenção')) {
                    modalDescription.innerHTML = `
                        <p>${description}</p>
                        <p class="mt-2">Durante este período, os seguintes serviços estarão indisponíveis:</p>
                        <ul>
                            <li>Emissão de documentos</li>
                            <li>Agendamentos online</li>
                            <li>Consulta de processos</li>
                        </ul>
                        <p class="mt-2">Pedimos desculpas pelo inconveniente. Esta manutenção é necessária para melhorias no sistema.</p>
                    `;
                } else {
                    modalDescription.innerHTML = `<p>${description}</p>`;
                }
                
                // Show modal
                notificationModal.classList.remove('hidden');
                
                // Mark as read if unread
                if (isUnread) {
                    card.classList.remove('unread');
                    card.classList.add('read');
                    const badge = card.querySelector('.inline-flex');
                    badge.className = 'inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium bg-gray-100 text-gray-600';
                    badge.textContent = 'Lida';
                    
                    // Update counter
                    const counter = document.querySelector('.text-sm.text-gray-600');
                    const currentCount = parseInt(counter.textContent.split(' ')[0]);
                    if (currentCount > 0) {
                        counter.textContent = `${currentCount - 1} não lidas`;
                        const badge = document.querySelector('.h-6.w-6.rounded-full');
                        badge.textContent = currentCount - 1;
                    }
                }
            });
        });
        
        closeModal.addEventListener('click', () => {
            notificationModal.classList.add('hidden');
        });
        
        // Close modal when clicking outside
        notificationModal.addEventListener('click', (e) => {
            if (e.target === notificationModal) {
                notificationModal.classList.add('hidden');
            }
        });
        
        // Mark all as read
        const markAllReadBtn = document.querySelector('.text-primary');
        markAllReadBtn.addEventListener('click', () => {
            const unreadCards = document.querySelectorAll('.notification-card.unread');
            unreadCards.forEach(card => {
                card.classList.remove('unread');
                card.classList.add('read');
                const badge = card.querySelector('.inline-flex');
                badge.className = 'inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium bg-gray-100 text-gray-600';
                badge.textContent = 'Lida';
            });
            
            // Update counter
            const counter = document.querySelector('.text-sm.text-gray-600');
            counter.textContent = '0 não lidas';
            const badge = document.querySelector('.h-6.w-6.rounded-full');
            badge.textContent = '0';
        });
        
        // Delete old notifications
        const deleteOldBtn = document.querySelector('.text-gray-600.hover\\:text-gray-800');
        deleteOldBtn.addEventListener('click', () => {
            const readCards = document.querySelectorAll('.notification-card.read');
            readCards.forEach(card => {
                card.remove();
            });
            
            // Check if there are any notifications left
            const remainingCards = document.querySelectorAll('.notification-card');
            if (remainingCards.length === 0) {
                document.getElementById('notifications-container').classList.add('hidden');
                document.getElementById('empty-state').classList.remove('hidden');
            }
        });
        
        // View details buttons
        const viewDetailsButtons = document.querySelectorAll('.view-details-btn');
        viewDetailsButtons.forEach(button => {
            button.addEventListener('click', (e) => {
                e.stopPropagation(); // Prevent card click
                const card = button.closest('.notification-card');
                card.click(); // Trigger card click to open modal
            });
        });
        
        // Toggle empty state for demo purposes
        const emptyStateToggle = document.querySelector('#empty-state button');
        emptyStateToggle.addEventListener('click', () => {
            document.getElementById('empty-state').classList.add('hidden');
            document.getElementById('notifications-container').classList.remove('hidden');
        });
   
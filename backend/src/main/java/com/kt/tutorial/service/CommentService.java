package com.kt.tutorial.service;

import com.kt.tutorial.dto.CommentDTO;
import com.kt.tutorial.model.Comment;
import com.kt.tutorial.model.Project;
import com.kt.tutorial.model.User;
import com.kt.tutorial.repository.CommentRepository;
import com.kt.tutorial.repository.ProjectRepository;
import com.kt.tutorial.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    @Transactional
    public CommentDTO createComment(CommentDTO commentDTO) {
        Project project = projectRepository.findById(commentDTO.getProjectId())
                .orElseThrow(() -> new RuntimeException("Project not found"));
        User author = userRepository.findById(commentDTO.getAuthorId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Comment comment = new Comment();
        comment.setContent(commentDTO.getContent());
        comment.setProject(project);
        comment.setAuthor(author);

        Comment savedComment = commentRepository.save(comment);
        return convertToDTO(savedComment);
    }

    @Transactional(readOnly = true)
    public List<CommentDTO> getCommentsByProject(Long projectId) {
        return commentRepository.findByProjectId(projectId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public CommentDTO updateComment(Long id, CommentDTO commentDTO) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        if (!comment.getAuthor().getId().equals(commentDTO.getAuthorId())) {
            throw new RuntimeException("User is not authorized to update this comment");
        }

        comment.setContent(commentDTO.getContent());
        Comment updatedComment = commentRepository.save(comment);
        return convertToDTO(updatedComment);
    }

    @Transactional
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }

    private CommentDTO convertToDTO(Comment comment) {
        CommentDTO dto = new CommentDTO();
        dto.setId(comment.getId());
        dto.setContent(comment.getContent());
        dto.setProjectId(comment.getProject().getId());
        dto.setAuthorId(comment.getAuthor().getId());
        dto.setAuthorName(comment.getAuthor().getName());
        dto.setCreatedAt(comment.getCreatedAt());
        dto.setUpdatedAt(comment.getUpdatedAt());
        return dto;
    }
} 
CREATE TABLE IF NOT EXISTS days (
                             id BIGSERIAL PRIMARY KEY NOT NULL,
                             day DATE NOT NULL,
                             is_passed BOOLEAN NOT NULL,
                             is_present BOOLEAN NOT NULL,
                             is_match BOOLEAN NOT NULL
);

@Column(name = "date")
    private LocalDate date;

    @Column(name = "is_passed")
    private boolean isPassed;

    @Column(name = "is_present")
    private boolean isPresent;

    @Column(name = "is_match")
    private boolean isMatch;

    @Column(name = "count_of_tour")
    private int countOfTour;

    @OneToMany(mappedBy = "tourDay")
    private List<Match> matches;

    @Type(type = "string-array")
    @Column(name = "changes", columnDefinition = "text[]")
    private String[] noteOfChanges;